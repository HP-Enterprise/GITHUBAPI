package com.incar.gitApi.service;

import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.WorkDetail;
import com.incar.gitApi.period.PeriodFactory;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.repository.WorkDetailRepository;
import com.incar.gitApi.util.DateUtil;
import com.incar.gitApi.util.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2016/7/9.
 */
@Service
public class WorkDetailService {
    private Date dueOn;
    private Date end;
    @Autowired
    private GitResultRepository gitResultRepository;
    @Autowired
    private WorkService workService;
    @Autowired
    private WorkDetailRepository workDetailRepository;


    /**
     * 删除详细信息
     */

    public void deleteWorkDetailInfo(){
        workDetailRepository.deleteAll();
    }

    /**
     * 保存所有的WorkDetail信息
     */
    public void saveWorkDetailInfo(){
        try{ List<WorkDetail> workDetails= getWorkDetailInfo();
            workDetailRepository.save(workDetails);
        }catch (Exception e){
         e.printStackTrace();
        }
    }

    /**
     * 获取所有WorkDetail信息
     * @return
     */
    public List<WorkDetail> getWorkDetailInfo(){
       Properties properties = workService.getRealnameProperties();
      List<GitResult> gitResults= gitResultRepository.findAll();
      List<GitResult> gitResults1= workService.getGitRetHasLabelHOrD(gitResults);
      List<WorkDetail> workDetails =new ArrayList<>();
        for (GitResult gitResult : gitResults1) {
         //   Object obj = properties.get(gitResult.getAssignee());
            if(gitResult.getAssignee()!=null) {//防止插入真实姓名出现空指针异常
                WorkDetail workDetail = new WorkDetail();
                workDetail.setState(gitResult.getState());
                workDetail.setUserName(gitResult.getAssignee());
                workDetail.setRealName((String) properties.get(gitResult.getAssignee()));
                workDetail.setState(gitResult.getState());
                workDetail.setTitle(gitResult.getTitle());
                workDetail.setProject(gitResult.getProject());
                workDetail.setActualTime(this.oneIssueActuWork(gitResult));
                workDetail.setExpectedTime(workService.oneIssueWork(gitResult));
                workDetail.setEfficiency(this.oneIssueEffic(gitResult));
                workDetail.setWeek(this.oneIssueWeek(gitResult));
                workDetail.setMonth(this.oneIssueMonth(gitResult));
                workDetail.setQuarter(this.oneIssueQuarter(gitResult));
                workDetail.setYear(this.oneIssueYear(gitResult));
                workDetails.add(workDetail);
            }
        }
        return workDetails;
    }

    /**
     * 计算某个issue的实际工作时间
     * @param gitResult
     * @return
     */

    public int  oneIssueActuWork(GitResult gitResult){
        end = gitResult.getClosedAt();
        if(end==null){
            end= new Date();
        }
    int actualTime  =  PeriodFactory.getHours(gitResult.getCreatedAt(), end);
        return actualTime;
    }

    /**
     * 计算某个issue属于哪一周
     * @param gitResult
     * @return
     */
    public int oneIssueWeek(GitResult gitResult){
          dueOn= gitResult.getDueOn();
        if(dueOn==null) {
            if(gitResult.getState().equals("open")) {
                dueOn = gitResult.getCreatedAt();
            }else{
                dueOn = gitResult.getClosedAt();
            }
        }
      int week=  DateUtil.getIssueWeek(dueOn);
        return week;
    }

    /**
     * 计算某个issue属于哪一月
     * @param gitResult
     * @return
     */
    public int oneIssueMonth(GitResult gitResult){
        dueOn= gitResult.getDueOn();
        if(dueOn==null) {
            if(gitResult.getState().equals("open")) {
                dueOn = gitResult.getCreatedAt();
            }else{
                dueOn = gitResult.getClosedAt();
            }
        }
        int month=DateUtil.getIssueMonth(dueOn);
        return month ;
    }

    /**
     * 计算某个issue属于哪一年
     * @param gitResult
     * @return
     */
    public int oneIssueYear(GitResult gitResult){
        dueOn= gitResult.getDueOn();
        if(dueOn==null) {
            if(gitResult.getState().equals("open")) {
                dueOn = gitResult.getCreatedAt();
            }else{
                dueOn = gitResult.getClosedAt();
            }
        }
        int year=DateUtil.getIssueYear(dueOn);
        return year ;
    }

    /**
     * 计算issue属于哪个季度
     * @param gitResult
     * @return
     */
    public int oneIssueQuarter(GitResult gitResult){
        dueOn= gitResult.getDueOn();
        if(dueOn==null) {
            if(gitResult.getState().equals("open")) {
                dueOn = gitResult.getCreatedAt();
            }else{
                dueOn = gitResult.getClosedAt();
            }
        }
        int month=DateUtil.getIssueMonth(dueOn);
        if(month==1||month==2||month==3){
            return 1;
        }else if(month==4||month==5||month==6){
            return 2;
        }else if(month==7||month==8||month==9){
            return 3;
        }else{
            return 4;
        }
    }

    /**
     * 计算某个issue的工作效率
     * @param gitResult
     * @return
     */
    public int oneIssueEffic(GitResult gitResult){
        int actualTime = oneIssueActuWork(gitResult);
        int expectTime=  workService.oneIssueWork(gitResult);
        if(actualTime>expectTime){
            return -1;
        }else if(actualTime==expectTime){
            return 0;
        }else {
            return 1;
        }
    }

    /**
     * 根据用户名和年、周，分页查询
     * @param userName 用户名
     * @param week  所属周
     * @param year  所属年
     * @param currentPage
     * @param pageSize
     * @return
     */
    public Page<WorkDetail> findPageOfWorkDetail(String userName,Integer week,Integer year,Integer currentPage,Integer pageSize){
        currentPage=(currentPage==null||currentPage<=0)?1:currentPage;
        pageSize=(pageSize==null||pageSize<=0)?10:pageSize;
        Pageable pageable = new PageRequest(currentPage-1,pageSize);
        Page<WorkDetail>  workDetailPage =  workDetailRepository.findPage(userName, week, year, pageable);
        return new PageImpl<WorkDetail>(workDetailPage.getContent(),pageable,workDetailPage.getTotalElements());
    }

    /**
     * 分页查询详细工作信息
     * @param userName 用户名
     * @param project 项目名
     * @param state issue状态
     * @param week 周
     * @param month 月
     * @param year  年
     * @param currentPage  当前页
     * @param pageSize  每页数量
     * @return json数据
     */
    public Page<WorkDetail> findPageOfWorkDetail(String userName,String project,String state,Integer week,Integer month,Integer quarter,Integer year,Integer currentPage,Integer pageSize){
        currentPage=(currentPage==null||currentPage<=0)?1:currentPage;
        pageSize=(pageSize==null||pageSize<=0)?10:pageSize;
//        userName = (userName.equals("")||userName==null)?null:userName;
//        project = (project.equals("")||project==null)?null:project;
//        state = (state.equals("")||state==null)?null:state;
        if(userName!=null){userName="%"+userName+"%";}//设置模糊查询
        if(project!=null){project="%"+project+"%";}
        if(state!=null){state="%"+state+"%";}
        Pageable pageable = new PageRequest(currentPage-1,pageSize,new Sort(Sort.Direction.DESC,"year"));
        Page<WorkDetail> workDetailPage= workDetailRepository.findPage(userName, project, state, week, month,quarter, year, pageable);
        return new PageImpl<WorkDetail>(workDetailPage.getContent(),pageable,workDetailPage.getTotalElements());
    }

    /**
     * 导出workDetail的excel数据
     * @param userName 用户名
     * @param project  项目
     * @param state  状态
     * @param week 周
     * @param month 月
     * @param quarter 季度
     * @param year 年
     * @return
     */
   public HSSFWorkbook findWorkDetailToExcel(String userName,String project,String state,Integer week,Integer month,Integer quarter,Integer year){
       if (userName!=null){userName="%"+userName+"%";}
       if (project!=null){project="%"+project+"%";}
       List<WorkDetail> workDetailList=  workDetailRepository.findExcel(userName, project, state, week, month, quarter, year);
       String[] tableHeader={"编号","用户名","姓名","预期时长（小时）","实际时长（小时）","问题","项目","状态","效率","周","月","季度","年"};
       String methods[]={"getId","getUserName","getRealName","getExpectedTime","getActualTime","getTitle","getProject","getState","getEfficiency","getWeek","getMonth","getQuarter","getYear"};
       return ExportExcelUtil.exprotExcel(tableHeader,methods,workDetailList);

   }

}

