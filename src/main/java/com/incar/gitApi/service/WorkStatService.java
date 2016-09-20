package com.incar.gitApi.service;

import com.incar.gitApi.entity.Work;

import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2016/9/18.
 */
@Service
public class WorkStatService {

    @Autowired
    public WorkRepository workRepository;
    @Autowired
    public GitResultRepository gitResultRepository;
    @Autowired
    public WorkService workService;

    public List<Work> getMonthWorkStat(int year, int month) {
        List<Work> monthWorkStat = new ArrayList<>();
     Properties properties= workService.getRealnameProperties();
        for (String str : gitResultRepository.findAllAssignee()) {
            if(str!=null){
            Work work1 = new Work();
            int sum1 = 0, sum2 = 0, sum3 = 0;
            List<Work> workList = workRepository.findAll(str, year, DateUtil.getMonthStartWeek(year, month), DateUtil.getMonthEndWeek(year, month));
            for (Work work : workList) {
                sum1 = sum1 + work.getFinishedWork();
                sum2 = sum2 + work.getUnfinishedWork();
                sum3 = sum3 + work.getWorkHours();
            }
            Object obj=   properties.getProperty(str);
            if(obj!=null){
                work1.setRealname((String)obj);
            }
            work1.setUsername(str);
            work1.setType(2);
            work1.setFinishedWork(sum1);
            work1.setUnfinishedWork(sum2);
            work1.setWorkHours(sum3);
            work1.setWeekInYear(month);
            work1.setWeekYear(year);
            monthWorkStat.add(work1);
            }
        }

        return monthWorkStat;

    }

    public List<Work> getQuarterWorkStat(int year, int quarter) {
        List<Work> monthWorkStat = new ArrayList<>();
        Properties properties= workService.getRealnameProperties();
        for (String str : gitResultRepository.findAllAssignee()) {
            if (str!= null) {
                Work work1 = new Work();
                int sum1 = 0, sum2 = 0, sum3 = 0;
                List<Work> workList = workRepository.findAll(str, year, DateUtil.getQuarterStartWeek(year, quarter), DateUtil.getQuarterEndWeek(year, quarter));
                for (Work work : workList) {
                    sum1 = sum1 + work.getFinishedWork();
                    sum2 = sum2 + work.getUnfinishedWork();
                    sum3 = sum3 + work.getWorkHours();
                }
                Object obj=   properties.getProperty(str);
                if(obj!=null){
                    work1.setRealname((String)obj);
                }
                work1.setUsername(str);
                work1.setType(3);
                work1.setFinishedWork(sum1);
                work1.setUnfinishedWork(sum2);
                work1.setWorkHours(sum3);
                work1.setWeekInYear(quarter);
                work1.setWeekYear(year);
                monthWorkStat.add(work1);
            }
        }
        return monthWorkStat;
    }

    public List<Work> getYearWorkStat(int year,int week) {
        List<Work> monthWorkStat = new ArrayList<>();
        Properties properties= workService.getRealnameProperties();
        for (String str : gitResultRepository.findAllAssignee()) {
            if (str!= null) {
                Work work1 = new Work();
                int sum1 = 0, sum2 = 0, sum3 = 0;
                List<Work> workList = workRepository.findAll(str, year, 1, week);
                for (Work work : workList) {
                    sum1 = sum1 + work.getFinishedWork();
                    sum2 = sum2 + work.getUnfinishedWork();
                    sum3 = sum3 + work.getWorkHours();
                }
                Object obj=   properties.getProperty(str);
                if(obj!=null){
                    work1.setRealname((String)obj);
                }
                work1.setUsername(str);
                work1.setType(4);
                work1.setFinishedWork(sum1);
                work1.setUnfinishedWork(sum2);
                work1.setWorkHours(sum3);
                work1.setWeekInYear(year);
                work1.setWeekYear(year);
                monthWorkStat.add(work1);
            }
        }
        return monthWorkStat;
    }
    public void saveStat(int year, int month) {
        List<Work> workList = this.getMonthWorkStat(year, month);
        for (Work work : workList) {
            if(work.getUsername()!=null) {
                workRepository.save(work);
            }
        }

    }
    public void saveQuarterStat(int year, int quarter) {
        List<Work> workList = this.getQuarterWorkStat(year, quarter);
        for (Work work : workList) {
            if(work.getUsername()!=null) {
                workRepository.save(work);
            }
        }
    }
    public void saveYearStat(int year, int week) {
        List<Work> workList = this.getYearWorkStat(year, week);
        for (Work work : workList) {
            if(work.getUsername()!=null) {
                workRepository.save(work);
            }
        }
    }

    public Page<Work> findPageOfMonth(String realname, String username, Integer weekInYear,Integer year, Integer currentPage, Integer pageSize, Integer fuzzy, String orderByProperty, Integer ascOrDesc) {
        currentPage = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
        pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        username = username == "" ? null : username;
        Pageable pageRequest = new PageRequest(currentPage - 1, pageSize, new Sort(Sort.Direction.DESC, "weekInYear").and(new Sort(Sort.Direction.ASC, "username")));
            if (realname != null)
                realname = "%" + realname + "%";
            if (username != null)
                username = "%" + username + "%";
        Page<Work>   workPage = workRepository.findMonthPage(realname, username, weekInYear,year, pageRequest);
        return new PageImpl<Work>(workPage.getContent(), pageRequest, workPage.getTotalElements());
    }

    public Page<Work> findPageOfQuarter(String realname, String username, Integer weekInYear,Integer year, Integer currentPage, Integer pageSize, Integer fuzzy, String orderByProperty, Integer ascOrDesc) {
        currentPage = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
        pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        username = username == "" ? null : username;
        Pageable pageRequest = new PageRequest(currentPage - 1, pageSize, new Sort(Sort.Direction.DESC, "weekInYear").and(new Sort(Sort.Direction.ASC, "username")));
        if (realname != null)
            realname = "%" + realname + "%";
        if (username != null)
            username = "%" + username + "%";
        Page<Work>   workPage = workRepository.findQuarterPage(realname, username, weekInYear,year, pageRequest);
        return new PageImpl<Work>(workPage.getContent(), pageRequest, workPage.getTotalElements());
    }

    public Page<Work> findPageOfYear(String realname, String username, Integer weekInYear,Integer year, Integer currentPage, Integer pageSize, Integer fuzzy, String orderByProperty, Integer ascOrDesc) {
        currentPage = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
        pageSize = (pageSize == null || pageSize <= 0) ? 10 : pageSize;
        username = username == "" ? null : username;
        Pageable pageRequest = new PageRequest(currentPage - 1, pageSize, new Sort(Sort.Direction.DESC, "weekInYear").and(new Sort(Sort.Direction.ASC, "username")));
        if (realname != null)
            realname = "%" + realname + "%";
        if (username != null)
            username = "%" + username + "%";
        Page<Work>   workPage = workRepository.findYearPage(realname, username, weekInYear, year,pageRequest);
        return new PageImpl<Work>(workPage.getContent(), pageRequest, workPage.getTotalElements());
    }
}
