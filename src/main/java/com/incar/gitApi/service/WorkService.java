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


    /**
     * 查询所有人github帐号
     * @return
     */
    public List<String> getAllAssignee(){
        return gitResultRepository.findAllAssignee();
    }


    /**
     * 保存工作信息
     */
    public void saveWorkInfo(){
        List<Work> works = new ArrayList<>();
        for (String assignee : this.getAllAssignee() ){
            if(assignee!=null){
                Work work = getWorkInfo(assignee);
                works.add(work);
            }
        }
        workRepository.save(works);
    }


    /**
     * 保存某周工作信息
     * @param weekInYear
     */
    public void saveWorkInfo(int weekInYear){
        List<Work> works = new ArrayList<>();
        for (String assignee : this.getAllAssignee() ){
            if(assignee!=null){
                Work work = getWorkInfo(assignee,weekInYear);
                works.add(work);
            }
        }
        workRepository.save(works);
    }


    /**
     * 删除工作信息
     * @param weekInYear
     */
    public void deleteWorkInfo(int weekInYear){
        workRepository.deleteByWeek(weekInYear);
    }


    public void deleteWorkInfo(){
        int weekInYear = DateUtil.getWeekInYear();
        workRepository.deleteByWeek(weekInYear);
    }


    /**
     * 获取某周工作信息
     * @param assignee
     * @param weekOfYear
     * @return
     */
    public Work getWorkInfo(String assignee,int weekOfYear){
        return this.getWorkInfo(assignee,DateUtil.getYear(),weekOfYear);
    }


    /**
     * 获取本周工作信息
     * @param assignee
     * @return
     */
    public Work getWorkInfo(String assignee){
        return this.getWorkInfo(assignee, DateUtil.getWeekInYear());
    }


    /**
     * 获取某年某周工作信息
     * @param assignee
     * @param weekYear
     * @param weekOfYear
     * @return
     */
    public Work getWorkInfo(String assignee,int weekYear,int weekOfYear){
        Date start = DateUtil.getWeekStart(weekYear,weekOfYear);
        Date end = DateUtil.getWeekEnd(weekYear, weekOfYear);

        List<GitResult> openGitRets = this.getOpenGitRet(assignee,end);
        List<GitResult> closedGitRets = this.getClosedGitRet(assignee, start, end);

        List<GitResult> gitResults = new ArrayList<>();
        gitResults.addAll(this.getGitRetHasLabelHOrD(openGitRets));
        gitResults.addAll(this.getGitRetHasLabelHOrD(closedGitRets));

        List<Period> periods = PeriodFactory.generatePeriodList(weekYear, weekOfYear);

        Work work = new Work();
        work.setFinishedWork(this.getTotalFinishedWork(closedGitRets));
        work.setUnfinishedWork(this.getTotalUnfinishedWork(openGitRets));
        work.setWorkHours(this.getHoursInWork(gitResults,periods));
        work.setWeekInYear(weekOfYear);
        work.setName(assignee);
        return work;
    }


    public List<GitResult> getGitRetHasLabelHOrD(List<GitResult> gitResults){
        List<GitResult> gitResults1 = new ArrayList<>();
        for (GitResult gitResult : gitResults){
            if(hasLableHOrD(gitResult)){
                gitResults1.add(gitResult);
            }
        }
        return gitResults1;
    }

    /**
     * 查询已经关闭的issue结果
     * @param assignee
     * @param date1
     * @param date2
     * @return
     */
    public List<GitResult> getClosedGitRet(String assignee,Date date1 ,Date date2){
        return gitResultRepository.findClosedGitRet(assignee, "closed", date1, date2);
    }


    /**
     * 查询属于open状态并且截止时间在本周dueOn前的issue
     * @param assignee
     * @param dueOn
     * @return
     */
    public List<GitResult> getOpenGitRet(String assignee,Date dueOn){
        return gitResultRepository.findOpenGitRet(assignee, "open", dueOn);
    }


    /**
     * 获取某个assignee某年某周所有的issue结果
     * @param assignee
     * @param weekOfYear
     * @return
     */
    public List<GitResult> getAllGitRetOfWeek(String assignee,int year,int weekOfYear){
        Date start = DateUtil.getWeekStart(year, weekOfYear);
        Date end = DateUtil.getWeekEnd(year,weekOfYear);
        List<GitResult> gitResults = new ArrayList<>();
        gitResults.addAll(this.getClosedGitRet(assignee, start, end));
        gitResults.addAll(this.getOpenGitRet(assignee,end));
        return gitResults;
    }


    /**
     * 获取某个assignee本周所有的issue结果
     * @param assignee
     * @param weekOfYear
     * @return
     */
    public List<GitResult> getAllGitRetOfWeek(String assignee,int weekOfYear){
        return getAllGitRetOfWeek(assignee, DateUtil.getYear(), weekOfYear);
    }


    /**
     * 获取所有已完成工作量
     * @param gitResults
     * @return
     */
    public int totalFinishedWork(List<GitResult> gitResults){
        int totalFinished = 0;
        for(GitResult gitResult : gitResults){
            totalFinished += this.oneIssueWork(gitResult);
        }
        return totalFinished;
    }


    /**
     * 计算某个issue对应的工作量
     * @param gitResult
     * @return
     */
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


    /**
     * 判断是否包含h或d标签
     * @param gitResult
     * @return
     */
    public boolean hasLableHOrD(GitResult gitResult){
        String labels = gitResult.getLabels();
        Pattern pattern = Pattern.compile("([DH]\\d)");
        if(labels != null) {
            Matcher matcher = pattern.matcher(labels);
            if (matcher.find()) {
               return true;
            }
        }
        return false;
    }

    /**
     * 获取所有完成的工作量
     * @param gitResults
     * @return
     */
    public int getTotalFinishedWork(List<GitResult> gitResults){
        return this.totalFinishedWork(gitResults);
    }


    /**
     * 获取所有完成的工作量
     * @param assignee
     * @param date1
     * @param date2
     * @return
     */
    public int getTotalFinishedWork(String assignee,Date date1 ,Date date2){
        return this.totalFinishedWork(getClosedGitRet(assignee, date1, date2));
    }


    /**
     * 获取某个assignee所有未完成的工作量
     * @param assignee
     * @param end
     * @return
     */
    public int getUnfinishedWorkOfAssignee(String assignee,Date end){
        return getTotalUnfinishedWork(getOpenGitRet(assignee, end));
    }


    /**
     * 获取某个结果未完成的工作量
     * @param gitResult
     * @return
     */
    public int getUnfinishedWorkOfOneIssue(GitResult gitResult){
        int num = 0;
        if(isBeforeThisWeek(gitResult.getDueOn())) {
            return num = oneIssueWork(gitResult);
        }
        return num;
    }


    /**
     * 统计所有未完成工作
     * @param gitResults
     * @return
     */
    public int getTotalUnfinishedWork(List<GitResult> gitResults){
        int totalHours = 0;
        for(GitResult gitResult : gitResults){
            totalHours += this.getUnfinishedWorkOfOneIssue(gitResult);
        }
        return totalHours;
    }


    /**
     * 判断某个时间是否在本周之前
     * @param date
     * @return
     */
    public boolean isBeforeThisWeek(Date date){
        Date date1 = DateUtil.getWeekEnd();
        if(date != null && date.compareTo(date1)<=0){
            return true;
        }
        return false;
    }


    /**
     * 判断某个时间点是否在某个片段内
     * @param date
     * @param period
     * @return
     */
    public boolean isInPeriod(Date date , Period period){
        Date periodStart = getTimeOfPeriod(period);
        Date periodEnd = getEndTimeOfPeriod(period);
        if(date.compareTo(periodStart)>=0 && date.compareTo(periodEnd)<=0){
            return true;
        }
        return false;
    }


    /**
     * 获取period开始时所对应的date
     * @param period
     * @return
     */
    public Date getTimeOfPeriod(Period period){
        return DateUtil.getPeriodStart(period);
    }


    /**
     * 获取一个period结束时所对应的date
     * @param period
     * @return
     */
    public Date getEndTimeOfPeriod(Period period){
        return DateUtil.getPeriodEnd(period);
    }



    /**
     * 计算createdAt所在的period
     * @param createdAt
     * @param periods
     * @return
     */
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


    /**
     * 计算closedAt所在的period
     * @param closedAt
     * @param periods
     * @return
     */
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


    /**
     * 获取issue创建和关闭时间对应的period
     * @param gitResult
     * @param periods
     * @return 开始和最后一个period
     */
    public Period[] getPeriodOfGitRet(GitResult gitResult,List<Period> periods){
        Period[] periodsArr = new Period[2];
        Date createdAt = gitResult.getCreatedAt();
        Date closedAt = gitResult.getClosedAt();

        Period periodStart = getFirstPeriodAfterCreated(createdAt, periods);
        Period periodEnd = getLastPeriodBeforeClosed(closedAt, periods);

//        try {
            if(periodStart!=null && periodEnd != null){
                if( periodStart.getNumber()-periodEnd.getNumber()==1){
                    if( isNotInPeriod(createdAt) && isNotInPeriod(closedAt)){//如果createAt和closedAt在同一个period空隙
                        return periodsArr;
                    }
                    periodsArr[0] = periodStart;
                    periodsArr[1] = periodStart;
                    return periodsArr;
                }
            }
//        }catch (Exception e){
//            System.out.println("报错git："+gitResult);
//            e.printStackTrace();
//        }


        periodsArr[0] = periodStart;
        periodsArr[1] = periodEnd;
        return periodsArr;
    }


    /**
     * 找到创建时间（不在period内）之后的第一个period
     * @param created
     * @param periods
     * @return
     */
    public Period getFirstPeriodAfterCreated(Date created,List<Period> periods){
        Period lastPeriod = periods.get(periods.size()-1);
        if(created.compareTo(getEndTimeOfPeriod(lastPeriod)) > 0){
            return null;
        }
        if(created.compareTo(getTimeOfPeriod(lastPeriod))>=0){
            return lastPeriod;
        }
        for(Period period : periods){
            if(created.compareTo(getTimeOfPeriod(period))<0){
                return period;
            }
        }
        return null;
    }


    /**
     * 判断时间点是否在第一个period之前
     * @param createdAt
     * @param period 第一个period
     * @return
     */
    public boolean isBeforeFirstPeriod(Date createdAt,Period period){
        if(getTimeOfPeriod(period).compareTo(createdAt)>0){
            return true;
        }
        return false;
    }


    /**
     *判断某个时间点是否不在period里
     * @param createdAt
     * @return
     */
    public boolean isNotInPeriod(Date createdAt){
        Calendar calendar = DateUtil.calendar;
        calendar.setTime(createdAt);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour <9 || (hour >= 12 && hour <13) || hour >17 ){
            return true;
        }
        return false;
    }


    /**
     * 获取issue关闭时间前的最后一个period
     * @param closedAt
     * @param periods
     * @return
     */
    public Period getLastPeriodBeforeClosed(Date closedAt,List<Period> periods){
        if(closedAt==null){
            return periods.get(periods.size()-1);
        }
        for(int i=periods.size()-1;i>=0;i--){
            if(closedAt.compareTo(getTimeOfPeriod(periods.get(i))) > 0){
                return periods.get(i);
            }
        }
        return null;
    }


    /**
     * 统计工作时间
     * @param gitResults  周所对应所有gitret
     * @param periods 周所对应所有period
     * @return 工作时长
     */
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
        orderByProperty = orderByProperty ==null?"weekInYear":orderByProperty;
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
