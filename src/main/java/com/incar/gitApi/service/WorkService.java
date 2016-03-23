package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Work;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.util.DateUtil;
import com.incar.gitApi.util.GithubClientConfig;
import com.incar.gitApi.util.Period;
import com.incar.gitApi.util.PeriodFactory;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Service
public class WorkService {

    private String username;

    private String password;

    private GitResultRepository gitResultRepository;

    private GithubClientConfig githubClientConfig;

    private WorkRepository workRepository;


    @Autowired
    public void setGitResultRepository(GitResultRepository gitResultRepository){this.gitResultRepository = gitResultRepository;}

    @Autowired
    public void setGithubClientConfig(GithubClientConfig githubClientConfig){
        this.githubClientConfig = githubClientConfig;
    }

    @Autowired
    public void setWorkRepository(WorkRepository workRepository){
        this.workRepository = workRepository;
    }


    //查询所有任务执行人
    public List<String> getAllAssignee(){
        return gitResultRepository.findAllAssignee();
    }


    public void saveWorkInfo(){
        for (String assignee : this.getAllAssignee() ){
            if(assignee!=null){
                Work work = getWorkInfo(assignee);
                workRepository.save(work);
            }
        }
    }

    public Work getWorkInfo(String assignee,int weekOfYear){
        Calendar calendar = Calendar.getInstance();
        return this.getWorkInfo(assignee,calendar.get(Calendar.YEAR),weekOfYear);
    }


    public Work getWorkInfo(String assignee){
        Calendar calendar = Calendar.getInstance();
        return this.getWorkInfo(assignee, calendar.get(Calendar.WEEK_OF_YEAR));
    }

    //获取某个任务执行人的工作信息
    public Work getWorkInfo(String assignee,int weekYear,int weekOfYear){
        Date start = DateUtil.setWeekStart(weekYear,weekOfYear);
        Date end = DateUtil.setWeekEnd(weekYear, weekOfYear);

        List<GitResult> openGitRets = this.getOpenGitRet(assignee,end);
        List<GitResult> closedGitRets = this.getClosedGitRet(assignee, start, end);

        List<GitResult> gitResults = new ArrayList<>();
        gitResults.addAll(openGitRets);
        gitResults.addAll(closedGitRets);

        List<Period> periods = PeriodFactory.generatePeriodList(weekYear, weekOfYear);

        Work work = new Work();
        work.setFinishedWork(this.getTotalFinishedWork(closedGitRets));
        work.setUnfinishedWork(this.getTotalUnfinishedWork(openGitRets));
        work.setWorkHours(this.getHoursInWork(gitResults,periods));
        work.setWeekInYear(weekOfYear);
        work.setName(assignee);
        return work;
    }


    public List<GitResult> getClosedGitRet(String assignee,Date date1 ,Date date2){
        return gitResultRepository.findClosedGitRet(assignee, "closed", date1, date2);
    }

    //查询assignee属于open状态并且里程碑的时间在本周dueOn时间前（包括）的gitRet
    public List<GitResult> getOpenGitRet(String assignee,Date dueOn){
        return gitResultRepository.findOpenGitRet(assignee, "open", dueOn);
    }

    //获取某个assigne本周的gitRets
    public List<GitResult> getAllGitRetThisWeek(String assignee){
        Calendar calendar = Calendar.getInstance();
        return getAllGitRetOfWeek(assignee, calendar.get(Calendar.YEAR), calendar.get(Calendar.WEEK_OF_YEAR));
    }

    //获取某年某周某人的gitrets
    public List<GitResult> getAllGitRetOfWeek(String assignee,int year,int weekOfYear){
        Date start = DateUtil.setWeekStart(year, weekOfYear);
        Date end = DateUtil.setWeekEnd(year,weekOfYear);
        List<GitResult> gitResults = new ArrayList<>();
        gitResults.addAll(this.getClosedGitRet(assignee, start, end));
        gitResults.addAll(this.getOpenGitRet(assignee,end));
        return gitResults;
    }

    //获取某人今年某周的gitrets
    public List<GitResult> getAllGitRetOfWeek(String assignee,int weekOfYear){
        Calendar calendar = Calendar.getInstance();
        return getAllGitRetOfWeek(assignee, calendar.get(Calendar.YEAR), weekOfYear);
    }

    //获取某人今年本周的gitRets
    public List<GitResult> getAllGitRetOfWeek(String assignee){
        Calendar calendar = Calendar.getInstance();
        return getAllGitRetOfWeek(assignee, calendar.get(Calendar.WEEK_OF_YEAR));
    }

    //获取某年某周某人的gitrets
    public List<GitResult> getAllGitRetOfWeek(String assignee,Date start ,Date end){
        List<GitResult> gitResults = new ArrayList<>();
        gitResults.addAll(this.getClosedGitRet(assignee, start, end));
        gitResults.addAll(this.getOpenGitRet(assignee, end));
        return gitResults;
    }


    public int totalFinishedWork(List<GitResult> gitResults){
        int totalFinished = 0;
        for(GitResult gitResult : gitResults){
            totalFinished += this.oneIssueWork(gitResult);
        }
        return totalFinished;
    }


    public int oneIssueWork(GitResult gitResult){
        String labels = gitResult.getLabels();
        Pattern pattern = Pattern.compile("([DH]\\d)");
        int n = 0,sum = 0 ;
        if(labels != null) {
            Matcher matcher = pattern.matcher(labels);
            if (matcher.find()) {
                String workAmount = matcher.group(1);
                n = Integer.parseInt(String.valueOf(workAmount.charAt(1)));
                sum = workAmount.charAt(0) == 'H' ? n : 8 * n;
            }
        }
        return sum;
    }


    public int getTotalFinishedWork(List<GitResult> gitResults){
        return this.totalFinishedWork(gitResults);
    }

    public int getTotalFinishedWork(String assignee,Date date1 ,Date date2){
        return this.totalFinishedWork(getClosedGitRet(assignee, date1, date2));
    }


    public int getUnfinishedWorkOfAssignee(String assignee,Date end){
        return getTotalUnfinishedWork(getOpenGitRet(assignee,end));
    }


    public int getUnfinishedWorkOfOneIssue(GitResult gitResult){
        int num = 0;
        if(isBeforeThisWeek(gitResult.getDueOn())) {
            return num = oneIssueWork(gitResult);
        }
        return num;
    }


    public int getTotalUnfinishedWork(List<GitResult> gitResults){
        int totalHours = 0;
        for(GitResult gitResult : gitResults){
            totalHours += this.getUnfinishedWorkOfOneIssue(gitResult);
        }
        return totalHours;
    }

    //判断某个时间是否在本周之前
    public boolean isBeforeThisWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        Date date2 = calendar.getTime();
        if(date != null && date.compareTo(date2)<=0){
            return true;
        }
        return false;
    }


    //判断某个时间点是否在某个片段内
    public boolean isInPeriod(Date date , Period period){
        Calendar calendar = Calendar.getInstance();
//        System.out.println("第几周：" + calendar.get(Calendar.WEEK_OF_YEAR));
        calendar.setWeekDate(period.getYear(), period.getWeekOfYear(), period.getDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay());
        calendar.set(Calendar.MINUTE, period.getMinute());
        calendar.set(Calendar.SECOND,period.getSeconds());
        Date date1 = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay() + 1);
        Date date2 = calendar.getTime();
        if(date.compareTo(date1)>=0 && date.compareTo(date2)<0){
            return true;
        }
        return false;
    }


    //获取period对应的date
    public Date getTimeOfPeriod(Period period){
        Calendar calendar = Calendar.getInstance();
        calendar.setWeekDate(period.getYear(), period.getWeekOfYear(), period.getDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, period.getHourOfDay());
        calendar.set(Calendar.MINUTE, period.getMinute());
        calendar.set(Calendar.SECOND,period.getSeconds());
        return calendar.getTime();
    }


    //计算createdAt所在的period
    public Period getPeriodOfCreated(Date createdAt,List<Period> periods){

        if(isBeforeFirstPeriod(createdAt,periods.get(0))){
            return periods.get(0);
        }
        if(isNotInPeriod(createdAt)){
            return getFirstPeriodAfterCreated(createdAt, periods);
        }
        for(Period period : periods){
            if(isInPeriod(createdAt,period)){
                return period;
            }
        }
        return null;
    }


    //计算closedAt所在的period
    public Period getPreiodOfClosed(Date closedAt ,List<Period> periods){
        //如果closedAt为空 将period设置为最后一个
        if(closedAt == null){
            return periods.get(periods.size()-1);
        }
        //如果closedAt在period空隙
        if(isNotInPeriod(closedAt)){
            return getLastPeriodBeforeClosed(closedAt, periods);
        }

        for(Period period : periods){
            if(isInPeriod(closedAt,period)){
                return period;
            }
        }

        return null;
    }

    public Period[] getPeriodOfGitRet(GitResult gitResult,List<Period> periods){
        Period[] periodsArr = new Period[2];
        Date createdAt = gitResult.getCreatedAt();
        Date closedAt = gitResult.getClosedAt();

        Period periodStart = getFirstPeriodAfterCreated(createdAt, periods);
        Period periodEnd = getLastPeriodBeforeClosed(closedAt, periods);

        try {
            if(periodStart.getNumber()-periodEnd.getNumber()==1){
                if( isNotInPeriod(createdAt) && isNotInPeriod(closedAt)){//如果createAt和closedAt在同一个period空隙
                    return periodsArr;
                }
                periodsArr[0] = periodStart;
                periodsArr[1] = periodStart;
                return periodsArr;
            }
        }catch (Exception e){
            System.out.println("报错git："+gitResult);
            e.printStackTrace();
        }


        periodsArr[0] = getFirstPeriodAfterCreated(createdAt, periods);
        periodsArr[1] = getLastPeriodBeforeClosed(closedAt,periods);
        return periodsArr;
    }

//    public boolean isInOnePeriod(Period period1,Period period2){
//        if(period1.getNumber()-period2.getNumber()==1){
//            return true;
//        }
//        return false;
//    }




    //找到创建时间（不在period内）之后的第一个period
    public Period getFirstPeriodAfterCreated(Date created,List<Period> periods){
        for(Period period : periods){
            if(getTimeOfPeriod(period).compareTo(created)>0){
                return period;
            }
        }
        return null;
    }


    //判断createdAt是否在第一个period之前
    public boolean isBeforeFirstPeriod(Date createdAt,Period period){
        if(getTimeOfPeriod(period).compareTo(createdAt)>0){
            return true;
        }
        return false;
    }


    //判断某个时间点是否在period的空隙里面
    public boolean isNotInPeriod(Date createdAt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour <9 || (hour >= 12 && hour <13) || hour >17 ){
            return true;
        }
        return false;
    }


    //找到关闭时间（不在period内）前的最后一个period
    public Period getLastPeriodBeforeClosed(Date closedAt,List<Period> periods){
        if(closedAt==null){
            return periods.get(periods.size()-1);
        }
        for(int i=periods.size()-1;i>=0;i--){
            if(getTimeOfPeriod(periods.get(i)).compareTo(closedAt) < 0){
//                System.out.println("periods.get(i)"+periods.get(i));
                return periods.get(i);
            }
        }
        return null;
    }


    //统计某个assignee本周所有的GitRet
    public int getHoursInWork(List<GitResult> gitResults,List<Period> periods){
        int n = 0;
        for (GitResult gitResult : gitResults){
            Period[] periodRet = getPeriodOfGitRet(gitResult, periods);
            if(periodRet[0] == null || periodRet[1] == null)
                continue;
            if(periodRet[0].getNumber()> periodRet[1].getNumber()){
                System.out.println("periods ex:"+gitResults);
                throw new RuntimeException("invalid parameter");
            }
            if(periodRet[0].getNumber() ==  periodRet[1].getNumber()){
                if(!periodRet[0].getIsInWork()){
                    periodRet[0].setIsInWork(true);
                    n++;
                }
                continue;
            }
            for(int i= periodRet[0].getNumber();i<= periodRet[1].getNumber();i++){
                if( !periods.get(i).getIsInWork()) {
                    periods.get(i).setIsInWork(true);
                    n++;
                }
            }
        }
        return n;
    }

    /**
     *
     * @param assignee issue执行者
     * @param weekInYear 周数
     * @param currentPage 当前页
     * @param pageSize 每页大小
     * @param fuzzy 是否模糊查询 1 表示为模糊查询
     * @param orderByProperty 排序属性
     * @param ascOrDesc 升序或降序 1表示升序，其他表示降序
     * @return
     */
    public Page<Work> findPageOfWork(String assignee,Integer weekInYear,Integer currentPage,Integer pageSize,Integer fuzzy,String orderByProperty,Integer ascOrDesc){
        currentPage = currentPage == null?1:(currentPage <= 0?1:currentPage);
        pageSize = pageSize == null?100:(pageSize <= 0?100:pageSize);
        boolean isFuzzy = fuzzy == null?false:(fuzzy==1?true:false);
        assignee = assignee==""?null:assignee;
        orderByProperty = orderByProperty ==null?"name":orderByProperty;
        ascOrDesc = ascOrDesc==null?0:(ascOrDesc !=1 ?0:1);
        Sort.Direction direction = ascOrDesc==1? Sort.Direction.ASC:Sort.Direction.DESC;
        Pageable pageRequest = new PageRequest(currentPage-1,pageSize,new Sort(orderByProperty));
        Page<Work> workPage ;
        if(isFuzzy && assignee != null){
            assignee = "%"+assignee+"%";
            workPage = workRepository.fuzzyFindPage(assignee,weekInYear,pageRequest);
        }else {
            workPage = workRepository.findPage(assignee,weekInYear,pageRequest);
        }
        return new PageImpl<Work>(workPage.getContent(),pageRequest,workPage.getTotalElements());
    }

}
