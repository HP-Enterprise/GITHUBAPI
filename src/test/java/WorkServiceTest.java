import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Work;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.period.Period;
import com.incar.gitApi.period.PeriodFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/20 0020.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
//@Transactional
public class WorkServiceTest {

    @Autowired
    private WorkService workService;

    @Autowired
    private GithubClientConfig githubClientConfig;

    @Autowired
    private GitResultRepository gitResultRepository;

    private List<Period> periods;

    private SimpleDateFormat sdf;


    @Before
    public void setup(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    }

//    @Test
//    public void testGetFinishedWorkAmount() throws ParseException {
//
//        Calendar calendar = Calendar.getInstance();
//        Date date1 = sdf.parse("2016-03-17 00-00-00");
//        Date date2 = sdf.parse("2016-03-26 24-00-00");
//
//        int result = workService.getTotalFinishedWork("Gupeng133", date1, date2);
//        System.out.println("result:"+result);
//    }

//    @Test
//    public void testIsInThisWeek() throws ParseException {
//        Date date = sdf.parse("2016-03-26 16-00-00");
//        boolean isIn = workService.isBeforeThisWeek(date);
//        System.out.println("isIn ?"+isIn);
//    }
//
//    @Test
//    public void testGetUnfinishedWorkOfAssignee() throws ParseException {
//        int hours = workService.getUnfinishedWorkOfAssignee("Septemberwh",sdf.parse("2016-03-26 00-00-00"));
//        System.out.println("work unfinished:" + hours);
//    }

//    @Test
//    public void testGenaratePeriods(){
//        List<Period> periods = workService.generatePeriodList();
//        System.out.println(periods.toString());
//        Assert.assertEquals(40, periods.size());
//    }



//    @Test
//    public void testPeriodOfCreatedAt() throws ParseException {
//        long time1 = System.currentTimeMillis();
//        Period period = workService.getPeriodOfCreated(sdf.parse("2016-03-19 13-22-00"), periods);
//        Period period1 = workService.getPeriodOfCreated(sdf.parse("2016-03-22 07-22-00"), periods);
//        Period period2 = workService.getPeriodOfCreated(sdf.parse("2016-03-22 12-20-00"), periods);
//        Period period3 = workService.getPeriodOfCreated(sdf.parse("2016-03-22 11-20-00"), periods);
//        long time2 = System.currentTimeMillis();
//
//        System.out.println("period:"+period);
//        System.out.println("period1:"+period1);
//        System.out.println("period2:"+period2);
//        System.out.println("period3:"+period3);
//        System.out.println("耗时a："+(time2-time1)+"ms");
//    }

//    @Test
//    public void testPeriodOfClosedAt() throws ParseException {
//        long time1 = System.currentTimeMillis();
//        Period period = workService.getPreiodOfClosed(null, periods);//没有关闭的issue
//        Period period1 = workService.getPreiodOfClosed(sdf.parse("2016-03-22 07-22-00"), periods);//下班时间关闭的issue
//        Period period2 = workService.getPreiodOfClosed(sdf.parse("2016-03-22 12-20-00"), periods);//午休时间关闭的issue
//        Period period3 = workService.getPreiodOfClosed(sdf.parse("2016-03-22 11-20-00"), periods);//上班时间关闭的issue
//        long time2 = System.currentTimeMillis();
//
//        System.out.println("period:"+period);
//        System.out.println("period1:"+period1);
//        System.out.println("period2:"+period2);
//        System.out.println("period3:"+period3);
//        System.out.println("耗时b："+(time2-time1)+"ms");
//    }


//    @Test
//    public void testGetWorkHoursOfAssignee() throws ParseException {
////        String assgnee = "wangh2015";
////        List<GitResult> gitResults = workService.getAllGitRetOfWeek(assgnee, 13);
////        System.out.println("gitResults"+gitResults);
////        int n = workService.getHoursInWork(gitResults,periods);
////        System.out.println("hour in work of  "+assgnee+" : "+n);
//
////        String assgnee1 = "Septemberwh";
////        List<GitResult> gitResults1 = workService.getAllGitRetOfWeek(assgnee1, 13);
////        System.out.println("gitResults"+gitResults1);
////        int n1 = workService.getHoursInWork(gitResults1,periods);
////        System.out.println("hour in work of  "+assgnee1+" : "+n1);
//
//        String assgnee2 = "Gupeng133";
//        List<GitResult> gitResults2 = workService.getAllGitRetOfWeek(assgnee2, 13);
//        System.out.println("gitResults"+gitResults2);
//        int n2 = workService.getHoursInWork(gitResults2,periods);
//        System.out.println("hour in work of  "+assgnee2+" : "+n2);
//    }
//
//
//    @Test
//    public void testFindOpenGitRet() throws ParseException {
//        String assgnee = "Septemberwh";
//        List<GitResult> gitResults = workService.getOpenGitRet(assgnee,sdf.parse("2016-03-27 00-00-00"));
//        System.out.println("testFindOpenGitRet()"+gitResults);
//    }
//
//    @Test
//    public void testFindClosedGitRet() throws ParseException {
//        String assgnee = "Gupeng133";
//        List<GitResult> gitResults = workService.getClosedGitRet(assgnee, sdf.parse("2016-03-20 00-00-00"), sdf.parse("2016-03-26 23-59-59"));
//        System.out.println(gitResults);
//    }


    @Test
    @Transactional
    @Rollback(true)
    public void testSaveWorkInfo(){
//        workService.saveWorkInfo(20);
        long start = System.currentTimeMillis();
        workService.saveWorkInfo(1);
        workService.saveWorkInfo(2);
        workService.saveWorkInfo(3);
        workService.saveWorkInfo(4);
        workService.saveWorkInfo(5);
        workService.saveWorkInfo(6);
        workService.saveWorkInfo(7);
        workService.saveWorkInfo(8);
        workService.saveWorkInfo(9);
        workService.saveWorkInfo(10);
        workService.saveWorkInfo(12);
        workService.saveWorkInfo(13);
        workService.saveWorkInfo(14);
        workService.saveWorkInfo(15);
        workService.saveWorkInfo(16);
        workService.saveWorkInfo(17);
        workService.saveWorkInfo(19);
        workService.saveWorkInfo(20);
        workService.saveWorkInfo(21);
        workService.saveWorkInfo(22);
        workService.saveWorkInfo(23);
        workService.saveWorkInfo(24);
        workService.saveWorkInfo(25);
        long end = System.currentTimeMillis();
        System.out.println("耗时:"+(end-start)+"毫秒");
    }

//    @Test
//    public void testGetWorkInfo(){
//        workService.getWorkInfo("ThomasChant");
//    }


//    @Test
//    public void testGetPeriodOfGitRet(){
//        GitResult gitResult = gitResultRepository.findByIssueId(142304643);
//       Period[] periodsArr =  workService.getPeriodOfGitRet(gitResult, periods);
//        System.out.println(periodsArr.toString());
//        GitResult gitResult1 = gitResultRepository.findByIssueId(140625278);
//        Period[] periodsArr1 =  workService.getPeriodOfGitRet(gitResult1, periods);
//        System.out.println(periodsArr1.toString());
//    }
//
//    @Test
//    public void testGetPeriodsByNow(){
//        List<Period> periods = PeriodFactory.generatePeriodList();
//        List<Period> periods1 = workService.getPeriodsByEnd(new Date(), periods);
//        System.out.println(periods1.size());
//    }

//    @Test
//    public void testGetWorkHoursByNow(){
//        List<GitResult> gitResults1 = workService.getAllGitRetOfWeek("Septemberwh",2016,16);
//        List<Period> periods = PeriodFactory.generatePeriodList();
//        List<Period> periods1 = workService.getPeriodsByEnd(new Date(), periods);
//        int hours = workService.getHoursInWork(gitResults1,periods1);
//        Assert.assertTrue(hours>0);
//    }

//    @Test
//    public void testGetWorkInfoOfWeeks(){
//         Work work1 = workService.getWorkInfo("ThomasChant", 10);
//        Work work2 = workService.getWorkInfo("db5433", 14);
//        Work work3 = workService.getWorkInfo("tiandashen");
//        Work work4 = workService.getWorkInfo("TeemolSparrow",17);
//        Work work5 = workService.getWorkInfo("TeemolSparrow",18);
//        Work work6 = workService.getWorkInfo("TeemolSparrow",19);
//        Work work7 = workService.getWorkInfo("TeemolSparrow",20);
//        Work work8 = workService.getWorkInfo("TeemolSparrow",21);
//        Work work9 = workService.getWorkInfo("TeemolSparrow",22);
//        Work work10 = workService.getWorkInfo("wyang181300", 11);
//        Work work11 = workService.getWorkInfo("mqwangincar", 14);
//        Work work12  = workService.getWorkInfo("bettermouse", 14);
//        assert work3!=null;
//    }

//    @Test
//    public void testPeriod(){
//        List<Period> periods = PeriodFactory.generatePeriodList(2016,15);
//        List<Period> periods1 = periods;
//        System.out.println("periods"+periods);
//        System.out.println("periods1"+periods1);
//
//    }

}
