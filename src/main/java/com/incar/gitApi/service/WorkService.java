package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Work;
import com.incar.gitApi.period.Period;
import com.incar.gitApi.period.PeriodFactory;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.util.DateUtil;
import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.util.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.Date;
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

    private static List<Period> periods = null;

    private static int previousVal = 0;

    @Autowired
    public void setGitResultRepository(GitResultRepository gitResultRepository) {
        this.gitResultRepository = gitResultRepository;
    }

    @Autowired
    public void setGithubClientConfig(GithubClientConfig githubClientConfig) {
        this.githubClientConfig = githubClientConfig;
    }

    @Autowired
    public void setWorkRepository(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }


    public static List<Period> getPeriods(int year, int weekOfYear) {
        if (weekOfYear != previousVal || periods == null) {
            periods = Arrays.asList(PeriodFactory.generatePeriods(year, weekOfYear));
        }
        previousVal = weekOfYear;
        return periods;
    }

    /**
     * 查询所有人github帐号
     *
     * @return
     */
    public List<String> getAllAssignee() {
        return gitResultRepository.findAllAssignee();
    }


    /**
     * 保存工作信息
     */
    public void saveWorkInfo() {
        List<Work> works = new ArrayList<>();
        Properties properties = getRealnameProperties();
        for (String assignee : this.getAllAssignee()) {
            if (assignee != null) {
                Work work = getWorkInfo(assignee);
                Object obj = properties.get(assignee);
                if (obj != null) {
                    work.setRealname((String) obj);
                }
                works.add(work);
            }
        }
        workRepository.save(works);
    }


    /**
     * 保存某周工作信息
     *
     * @param weekInYear
     */
    public void saveWorkInfo(int weekInYear) {
        List<Work> works = new ArrayList<>();
        Properties properties = getRealnameProperties();
        for (String assignee : this.getAllAssignee()) {
            if (assignee != null) {
                Work work = getWorkInfo(assignee, weekInYear);
                Object obj = properties.get(assignee);
                if (obj != null) {
                    work.setRealname((String) obj);
                }
                works.add(work);
            }
        }
        workRepository.save(works);
    }


    /**
     * 删除工作信息
     */
    public void deleteWorkInfo() {
        int weekInYear = DateUtil.getWeekInYear();
        workRepository.deleteByWeek(weekInYear);
    }


    /**
     * 获取某周工作信息
     *
     * @param assignee   用户
     * @param weekOfYear 星期数
     * @return
     */
    public Work getWorkInfo(String assignee, int weekOfYear) {
        return this.getWorkInfo(assignee, DateUtil.getYear(), weekOfYear);
    }


    /**
     * 获取本周工作信息
     *
     * @param assignee 用户
     * @return
     */
    public Work getWorkInfo(String assignee) {
        return this.getWorkInfo(assignee, DateUtil.getWeekInYear());
    }


    /**
     * 获取某周工作信息
     *
     * @param username
     * @param weekYear
     * @param weekOfYear
     * @return
     */
    public Work getWorkInfo(String username, int weekYear, int weekOfYear) {
        initPeriods(getPeriods(weekYear, weekOfYear));//初始化period
        Date start = DateUtil.getWeekStart(weekYear, weekOfYear);
        Date end = DateUtil.getWeekEnd(weekYear, weekOfYear);
        List<GitResult> openGitRets = this.getOpenGitRet(username, start, end);
        List<GitResult> closedGitRets = this.getClosedGitRet(username, start, end);
        List<GitResult> gitResultList=gitResultRepository.findAllGitRet(username);
        //获取到目前为止的所有period
        Date time = null;
        if (weekOfYear != DateUtil.getWeekInYear()) {
            time = DateUtil.getWeekEnd(weekYear, weekOfYear);
        } else {
            time = new Date();
        }
        List<Period> periods1 = this.getPeriodsByEnd(time, weekOfYear, periods);
        Work work = new Work();
        work.setFinishedWork(this.getTotalFinishedWork(closedGitRets));
        work.setUnfinishedWork(this.getTotalUnfinishedWork(openGitRets));
        work.setWorkHours(this.getHoursInWork(this.getGitRetHasLabelHOrD(gitResultList), weekOfYear, periods1));
        work.setWeekInYear(weekOfYear);
        work.setUsername(username);
//        System.out.println("工作信息结果：" + work);
        return work;
    }

    /**
     * 统计工作时间
     *
     * @param gitResults 周所对应所有gitret
     * @param periods    周所对应所有period
     * @return 工作时长
     */
    public int getHoursInWork(List<GitResult> gitResults, int weekOfYear, List<Period> periods) {
        int n = 0;
        for (GitResult gitResult : gitResults) {
            Period[] periodRet = getPeriodOfGitRet(gitResult, weekOfYear, periods);
            if (periodRet[0] == null || periodRet[1] == null)
                continue;
            if (periodRet[0].id() > periodRet[1].id()) {
                System.out.println("periods ex:" + gitResult);
                throw new RuntimeException("invalid parameter");
            }
            if (periodRet[0].id() == periodRet[1].id() && periodRet[0].isWorkTime()) {
                if (!periodRet[0].getIsInWork()) {
                    periodRet[0].setIsInWork(true);
                    n++;
                }
                continue;
            }
            for (int i = periodRet[0].id(); i <= periodRet[1].id(); i++) {
                if (!periods.get(i - 1).getIsInWork() && periods.get(i - 1).isWorkTime()) {
                    periods.get(i - 1).setIsInWork(true);
                    n++;
                }
            }
        }
        return n;
    }

    /**
     * 从配置文件中获取真实姓名
     *
     * @return
     */
    public Properties getRealnameProperties() {
        Properties properties = new Properties();
        try {
            String filePath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "realnames.properties";
            InputStreamReader br = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
            properties.load(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;

    }


    //获取一个时间之前的所有period
    public List<Period> getPeriodsByEnd(Date end, int weekOfYear, List<Period> periods) {
        List<Period> periods1 = new ArrayList<>();
        Period period1 = this.getPeriodClosedAt(end, periods, weekOfYear);
        if (period1 == null)
            return periods1;
        for (Period period : periods) {
            periods1.add(period);
            if (period1.id() == period.id()) {
                break;
            }
        }
        return periods1;
    }

    /**
     * 获取到包含h或d标签的issue
     *
     * @param gitResults
     * @return
     */
    public List<GitResult> getGitRetHasLabelHOrD(List<GitResult> gitResults) {
        List<GitResult> gitResults1 = new ArrayList<>();
        for (GitResult gitResult : gitResults) {
            if (hasLableHOrD(gitResult)) {
                gitResults1.add(gitResult);
            }
        }
        return gitResults1;
    }

    /**
     * 查询已经关闭的issue结果
     *
     * @param assignee
     * @param date1
     * @param date2
     * @return
     */
    public List<GitResult> getClosedGitRet(String assignee, Date date1, Date date2) {
        return gitResultRepository.findClosedGitRet(assignee, "closed", date1, date2);
    }


    /**
     * 查询属于open状态并且截止时间在本周dueOn前的issue
     *
     * @param assignee
     * @param weekStart
     * @return
     */
    public List<GitResult> getOpenGitRet(String assignee, Date weekStart, Date weekEnd) {
        return gitResultRepository.findOpenGitRet(assignee, "open", weekStart, weekEnd);
    }


    /**
     * 计算某个issue对应的工作量
     *
     * @param gitResult
     * @return
     */
    public int oneIssueWork(GitResult gitResult) {
        String labels = gitResult.getLabels();
        Pattern pattern = Pattern.compile("([DH]\\d)");
        int n = 0, sum = 0;
        if (labels != null) {
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
     *
     * @param gitResult
     * @return
     */
    public boolean hasLableHOrD(GitResult gitResult) {
        String labels = gitResult.getLabels();
        Pattern pattern = Pattern.compile("([DH]\\d)");
        if (labels != null) {
            Matcher matcher = pattern.matcher(labels);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取所有完成的工作量
     *
     * @param gitResults
     * @return
     */
    public int getTotalFinishedWork(List<GitResult> gitResults) {
        int totalFinished = 0;
        for (GitResult gitResult : gitResults) {
            totalFinished += this.oneIssueWork(gitResult);
        }
        return totalFinished;
    }

    /**
     * 统计所有未完成工作
     *
     * @param gitResults
     * @return
     */
    public int getTotalUnfinishedWork(List<GitResult> gitResults) {
        int totalHours = 0;
        for (GitResult gitResult : gitResults) {
            totalHours += this.oneIssueWork(gitResult);
        }
        return totalHours;
    }

    /**
     * 判断某个时间是否在本周之前
     *
     * @param date
     * @return
     */
    public boolean isBeforeThisWeek(Date date) {
        Date date1 = DateUtil.getWeekEnd();
        if (date != null && date.compareTo(date1) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断某个时间是否在本周后
     *
     * @param date
     * @return
     */
    public boolean isAfterThisWeek(Date date, List<Period> periods) {
        if (date != null && date.compareTo(periods.get(periods.size() - 1).end()) > 0) {
            return true;
        }
        return false;
    }


    /**
     * 判断某个时间点是否在某个片段内
     *
     * @param date
     * @param period
     * @return
     */
    public boolean isInPeriod(Date date, Period period) {
        if (DateUtil.compareDate(date, period.start()) >= 0 && DateUtil.compareDate(date, period.end()) <= 0) {
            return true;
        }
        return false;
    }


    /**
     * 获取issue创建和关闭时间对应的period
     *
     * @param gitResult
     * @param periods
     * @return 开始和最后一个period
     */
    public Period[] getPeriodOfGitRet(GitResult gitResult, int weekOfYear, List<Period> periods) {
        Period[] periodsArr = new Period[2];
        Date createdAt = gitResult.getCreatedAt();
        Date closedAt = gitResult.getClosedAt();
        periodsArr[0] = getPeriodCreateAt(createdAt, periods);
        periodsArr[1] = getPeriodClosedAt(closedAt, periods, weekOfYear);
        return periodsArr;
    }


    /**
     * 找到创建时间对应的period
     *
     * @param created
     * @param periods
     * @return
     */
    public Period getPeriodCreateAt(Date created, List<Period> periods) {
        //本周以前创建的issue，返回第一个period
        if (isBeforeFirstPeriod(created, periods)) {
            return periods.get(0);
        }
        for (Period period : periods) {
            if (isInPeriod(created, period)) {
                return period;
            }
        }
        return null;
    }


    /**
     * 判断issue是否是本周以前创建的
     *
     * @param createdAt
     * @param periods   period数组
     * @return
     */
    public boolean isBeforeFirstPeriod(Date createdAt, List<Period> periods) {
        if (periods.get(0).start().compareTo(createdAt) > 0) {
            return true;
        }
        return false;
    }


    /**
     * 获取issue结束时间对应的period
     *
     * @param closedAt
     * @param periods
     * @return
     */
    public Period getPeriodClosedAt(Date closedAt, List<Period> periods, int weekOfYear) {
        if (DateUtil.getWeekInYear() != weekOfYear) {
            if (closedAt == null || isAfterThisWeek(closedAt, periods)) {
                return periods.get(periods.size() - 1);
            } else {
                for (Period period : periods) {
                    if (isInPeriod(closedAt, period)) {
                        return period;
                    }
                }
            }
        } else {
            if (closedAt == null || isAfterThisWeek(closedAt, periods)) {
                for (Period period : periods) {
                    if (isInPeriod(new Date(), period)) {
                        return period;
                    }
                }
            } else {
                for (Period period : periods) {
                    if (isInPeriod(closedAt, period)) {
                        return period;
                    }
                }
            }
        }

        return null;
    }


    /**
     * 重新赋值
     *
     * @param periods
     */
    private static void initPeriods(List<Period> periods) {
        for (Period period : periods) {
            period.setIsInWork(false);
        }
    }


    /**
     * @param realname        真实姓名
     * @param username        github用户名
     * @param weekInYear      周数
     * @param currentPage     当前页
     * @param pageSize        每页大小
     * @param fuzzy           是否模糊查询 1 表示为模糊查询
     * @param orderByProperty 排序属性
     * @param ascOrDesc       升序或降序 1表示升序，其他表示降序
     * @return
     */

    public Page<Work> findPageOfWork(String realname, String username, Integer weekInYear, Integer currentPage, Integer pageSize, Integer fuzzy, String orderByProperty, Integer ascOrDesc) {
        currentPage = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
        pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        boolean isFuzzy = (fuzzy != null && fuzzy == 1) ? true : false;
        username = username == "" ? null : username;
        Pageable pageRequest = new PageRequest(currentPage - 1, pageSize, new Sort(Sort.Direction.DESC, "weekInYear").and(new Sort(Sort.Direction.ASC, "username")));
        Page<Work> workPage;
        if (isFuzzy) {
            if (realname != null)
                realname = "%" + realname + "%";
            if (username != null)
                username = "%" + username + "%";
            workPage = workRepository.fuzzyFindPage(realname, username, weekInYear, pageRequest);
        } else {
            workPage = workRepository.findPage(realname, username, weekInYear, pageRequest);
        }
        return new PageImpl<Work>(workPage.getContent(), pageRequest, workPage.getTotalElements());
    }

    /**
     * 导出excel表格
     *
     * @param realname   姓名
     * @param username   用户名
     * @param weekInYear 周
     * @return
     */
    public HSSFWorkbook findWorkToExcel(String realname, String username, Integer weekInYear) {
        if (realname != null) {
            realname = "%" + realname + "%";
        }
        if (username != null) {
            username = "%" + username + "%";
        }
        List<Work> workList = workRepository.findExcel(realname, username, weekInYear);
        String[] tableHeader = {"编号", "用户名", "姓名", "已完成工作时长（小时）", "未完成工作时长（小时）", "工作时长（小时）", "周"};
        String methods[] = {"getId", "getUsername", "getRealname", "getFinishedWork", "getUnfinishedWork）", "getWorkHours", "getWeekInYear"};
        return ExportExcelUtil.exprotExcel(tableHeader, methods, workList);
    }
}
