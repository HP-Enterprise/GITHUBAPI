package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Work;
import com.incar.gitApi.period.Period1;
import com.incar.gitApi.period.PeriodFactory1;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.util.DateUtil;
import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.period.Period;
import com.incar.gitApi.period.PeriodFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@Service
public class WorkService {

    private GitResultRepository gitResultRepository;

    private GithubClientConfig githubClientConfig;

    private WorkRepository workRepository;

    private static List<Period1> periods = null;

    private static int flag = 0;

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


    public static  List<Period1> getPeriods(int year,int weekOfYear){
        if(weekOfYear != flag || periods == null){
            periods = Arrays.asList(PeriodFactory1.generatePeriods(year,weekOfYear));
        }
        flag = weekOfYear;
        return periods;
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
        Properties properties = getRealnameProperties();
        for (String assignee : this.getAllAssignee() ){
            if(assignee!=null){
                Work work = getWorkInfo(assignee);
                Object obj = properties.get(assignee);
                if(obj!=null){
                    work.setRealname((String)obj);
                }
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
        Properties properties = getRealnameProperties();
        for (String assignee : this.getAllAssignee() ){
            if(assignee!=null){
                Work work = getWorkInfo(assignee,weekInYear);
                Object obj = properties.get(assignee);
                if(obj!=null){
                    work.setRealname((String)obj);
                }
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
        return this.getWorkInfo(assignee, DateUtil.getYear(), weekOfYear);
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
     * 获取某周工作信息
     * @param username
     * @param weekYear
     * @param weekOfYear
     * @return
     */
    public Work getWorkInfo(String username,int weekYear,int weekOfYear){
        initPeriods(getPeriods(weekYear,weekOfYear));
        Date start = DateUtil.getWeekStart(weekYear, weekOfYear);
        Date end = null;
        int thisWeekOfYear = DateUtil.getWeekInYear();
        if(thisWeekOfYear > weekOfYear){//查询是以前周的工作信息
            end = DateUtil.getWeekEnd(weekYear, weekOfYear);
        }else {
            end = new Date();
        }
        List<GitResult> openGitRets = this.getOpenGitRet(username, DateUtil.getWeekEnd(weekYear, weekOfYear));
        List<GitResult> closedGitRets = this.getClosedGitRet(username, start, end);

        List<GitResult> gitResults = new ArrayList<>();
        gitResults.addAll(this.getGitRetHasLabelHOrD(openGitRets));
        gitResults.addAll(this.getGitRetHasLabelHOrD(closedGitRets));

//        List<Period1> periods = Arrays.asList(PeriodFactory1.generatePeriods(weekYear, weekOfYear));

        //获取到目前为止的所有period
        List<Period1> periods1 =this.getPeriodsByEnd(end, periods);
        Work work = new Work();
        work.setFinishedWork(this.getTotalFinishedWork(closedGitRets));
        work.setUnfinishedWork(this.getTotalUnfinishedWork(openGitRets));
        work.setWorkHours(this.getHoursInWork(gitResults, periods));
        work.setWeekInYear(weekOfYear);
        work.setUsername(username);
        return work;
    }

    private Properties getRealnameProperties(){
        Properties properties = new Properties();
        try {
            String filePath = "src"+ File.separator+"main"+File.separator+"resources"+File.separator+"realnames.properties";
            InputStreamReader  br = new InputStreamReader(new FileInputStream(new File(filePath)), "GBK");
            properties.load(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }


    //获取一个时间之前的所有period
    public List<Period1> getPeriodsByEnd(Date end,List<Period1> periods){
        List<Period1> periods1 = new ArrayList<>();
        Period1 period1 = this.getPeriodClosedAt(end, periods);
        if(period1 == null)
            return  periods1;
        for(Period1 period : periods){
            periods1.add(period);
            if(period1.id()==period.id()){
                break;
            }
        }
        return periods1;
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
     * 获取某个结果未完成的工作量
     * @param gitResult
     * @return
     */
    public int getUnfinishedWorkOfOneIssue(GitResult gitResult){
        int num = 0;
        if(isBeforeThisWeek(gitResult.getDueOn())) {
            return oneIssueWork(gitResult);
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
    public boolean isInPeriod(Date date , Period1 period){
        if(date.compareTo(period.start())>=0 && date.compareTo(period.end())<=0){
            return true;
        }
        return false;
    }


    /**
     * 获取issue创建和关闭时间对应的period
     * @param gitResult
     * @param periods
     * @return 开始和最后一个period
     */
    public Period1[] getPeriodOfGitRet(GitResult gitResult,List<Period1> periods){
        Period1[] periodsArr = new Period1[2];
        Date createdAt = gitResult.getCreatedAt();
        Date closedAt = gitResult.getClosedAt();
        Period1 periodStart = getPeriodCreateAt(createdAt, periods);

        Period1 periodEnd = getPeriodClosedAt(closedAt, periods);

        periodsArr[0] = periodStart;
        periodsArr[1] = periodEnd;
        return periodsArr;

    }


    /**
     * 找到创建时间对应的period
     * @param created
     * @param periods
     * @return
     */
    public Period1 getPeriodCreateAt(Date created,List<Period1> periods){
        //上周创建的issue，返回第一个period
        if(isBeforeFirstPeriod(created,periods)){
            return periods.get(0);
        }
        for(Period1 period1 : periods){
            if (isInPeriod(created,period1)){
                return period1;
            }
        }
        return null;
    }


    /**
     * 判断issue是否是本周以前创建的
     * @param createdAt
     * @param periods period数组
     * @return
     */
    public boolean isBeforeFirstPeriod(Date createdAt,List<Period1> periods){
        if(periods.get(0).start().compareTo(createdAt)>0){
            return true;
        }
        return false;
    }


    /**
     * 获取issue结束时间对应的period
     * @param closedAt
     * @param periods
     * @return
     */
    public Period1 getPeriodClosedAt(Date closedAt,List<Period1> periods){
        if(closedAt==null){
            return periods.get(periods.size()-1);
        }
        for(Period1 period1: periods){
            if(isInPeriod(closedAt,period1)){
                return period1;
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
    public int getHoursInWork(List<GitResult> gitResults,List<Period1> periods){
        int n = 0;
        for (GitResult gitResult : gitResults){
            Period1[] periodRet = getPeriodOfGitRet(gitResult, periods);
            if(periodRet[0] == null || periodRet[1] == null)
                continue;
            if(periodRet[0].id()> periodRet[1].id()){
                System.out.println("periods ex:"+gitResult);
                throw new RuntimeException("invalid parameter");
            }
            if(periodRet[0].id() ==  periodRet[1].id() && periodRet[0].isWorkTime()){
                if(!periodRet[0].getIsInWork()){
                    periodRet[0].setIsInWork(true);
                    n++;
                }
                continue;
            }
            for(int i= periodRet[0].id();i<= periodRet[1].id();i++){
                if( !periods.get(i).getIsInWork() && periods.get(i).isWorkTime()) {
                    periods.get(i).setIsInWork(true);
                    n++;
                }
            }
        }
        return n;
    }

    /**
     * 重新赋值
     * @param period1s
     */
    private static void initPeriods(List<Period1> period1s){
        for(Period1 period1 : period1s){
            period1.setIsInWork(false);
        }
    }


    /**
     * @param realname 真实姓名
     * @param username github用户名
     * @param weekInYear 周数
     * @param currentPage 当前页
     * @param pageSize 每页大小
     * @param fuzzy 是否模糊查询 1 表示为模糊查询
     * @param orderByProperty 排序属性
     * @param ascOrDesc 升序或降序 1表示升序，其他表示降序
     * @return
     */
    public Page<Work> findPageOfWork(String realname,String username,Integer weekInYear,Integer currentPage,Integer pageSize,Integer fuzzy,String orderByProperty,Integer ascOrDesc){
        currentPage = (currentPage == null || currentPage <= 0)?1:currentPage;
        pageSize = (pageSize == null || pageSize <= 0)?10:pageSize;
        boolean isFuzzy = (fuzzy != null && fuzzy == 1)?true:false;
        username = username==""?null:username;
        Pageable pageRequest = new PageRequest(currentPage-1,pageSize,new Sort(Sort.Direction.DESC,"weekInYear"));
        Page<Work> workPage ;
        if(isFuzzy ){
            if(realname!=null)
                realname = "%"+realname+"%";
            if(username!=null)
                username = "%"+username+"%";
            workPage = workRepository.fuzzyFindPage(realname,username,weekInYear,pageRequest);
        }else {
            workPage = workRepository.findPage(realname,username,weekInYear,pageRequest);
        }
        return new PageImpl<Work>(workPage.getContent(),pageRequest,workPage.getTotalElements());
    }

}
