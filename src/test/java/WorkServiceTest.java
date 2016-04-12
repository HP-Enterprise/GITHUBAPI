import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.util.GithubClientConfig;
import com.incar.gitApi.util.Period;
import com.incar.gitApi.util.PeriodFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
        periods = PeriodFactory.generatePeriodList(13);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    }

    @Test
    public void testGetFinishedWorkAmount() throws ParseException {

        Calendar calendar = Calendar.getInstance();
        Date date1 = sdf.parse("2016-03-17 00-00-00");
        Date date2 = sdf.parse("2016-03-26 24-00-00");

        int result = workService.getTotalFinishedWork("Gupeng133", date1, date2);
        System.out.println("result:"+result);
    }

    @Test
    public void testIsInThisWeek() throws ParseException {
        Date date = sdf.parse("2016-03-26 16-00-00");
        boolean isIn = workService.isBeforeThisWeek(date);
        System.out.println("isIn ?"+isIn);
    }

    @Test
    public void testGetUnfinishedWorkOfAssignee() throws ParseException {
        int hours = workService.getUnfinishedWorkOfAssignee("Septemberwh",sdf.parse("2016-03-26 00-00-00"));
        System.out.println("work unfinished:" + hours);
    }

//    @Test
//    public void testGenaratePeriods(){
//        List<Period> periods = workService.generatePeriodList();
//        System.out.println(periods.toString());
//        Assert.assertEquals(40, periods.size());
//    }



    @Test
    public void testPeriodOfCreatedAt() throws ParseException {
        long time1 = System.currentTimeMillis();
        Period period = workService.getPeriodOfCreated(sdf.parse("2016-03-19 13-22-00"), periods);
        Period period1 = workService.getPeriodOfCreated(sdf.parse("2016-03-22 07-22-00"), periods);
        Period period2 = workService.getPeriodOfCreated(sdf.parse("2016-03-22 12-20-00"), periods);
        Period period3 = workService.getPeriodOfCreated(sdf.parse("2016-03-22 11-20-00"), periods);
        long time2 = System.currentTimeMillis();

        System.out.println("period:"+period);
        System.out.println("period1:"+period1);
        System.out.println("period2:"+period2);
        System.out.println("period3:"+period3);
        System.out.println("耗时a："+(time2-time1)+"ms");
    }

    @Test
    public void testPeriodOfClosedAt() throws ParseException {
        long time1 = System.currentTimeMillis();
        Period period = workService.getPreiodOfClosed(null, periods);//没有关闭的issue
        Period period1 = workService.getPreiodOfClosed(sdf.parse("2016-03-22 07-22-00"), periods);//下班时间关闭的issue
        Period period2 = workService.getPreiodOfClosed(sdf.parse("2016-03-22 12-20-00"), periods);//午休时间关闭的issue
        Period period3 = workService.getPreiodOfClosed(sdf.parse("2016-03-22 11-20-00"), periods);//上班时间关闭的issue
        long time2 = System.currentTimeMillis();

        System.out.println("period:"+period);
        System.out.println("period1:"+period1);
        System.out.println("period2:"+period2);
        System.out.println("period3:"+period3);
        System.out.println("耗时b："+(time2-time1)+"ms");
    }


    @Test
    public void testGetWorkHoursOfAssignee() throws ParseException {
//        String assgnee = "wangh2015";
//        List<GitResult> gitResults = workService.getAllGitRetOfWeek(assgnee, 13);
//        System.out.println("gitResults"+gitResults);
//        int n = workService.getHoursInWork(gitResults,periods);
//        System.out.println("hour in work of  "+assgnee+" : "+n);

//        String assgnee1 = "Septemberwh";
//        List<GitResult> gitResults1 = workService.getAllGitRetOfWeek(assgnee1, 13);
//        System.out.println("gitResults"+gitResults1);
//        int n1 = workService.getHoursInWork(gitResults1,periods);
//        System.out.println("hour in work of  "+assgnee1+" : "+n1);

        String assgnee2 = "Gupeng133";
        List<GitResult> gitResults2 = workService.getAllGitRetOfWeek(assgnee2, 13);
        System.out.println("gitResults"+gitResults2);
        int n2 = workService.getHoursInWork(gitResults2,periods);
        System.out.println("hour in work of  "+assgnee2+" : "+n2);
    }


    @Test
    public void testFindOpenGitRet() throws ParseException {
        String assgnee = "Septemberwh";
        List<GitResult> gitResults = workService.getOpenGitRet(assgnee,sdf.parse("2016-03-27 00-00-00"));
        System.out.println("testFindOpenGitRet()"+gitResults);
    }

    @Test
    public void testFindClosedGitRet() throws ParseException {
        String assgnee = "Gupeng133";
        List<GitResult> gitResults = workService.getClosedGitRet(assgnee, sdf.parse("2016-03-20 00-00-00"), sdf.parse("2016-03-26 23-59-59"));
        System.out.println(gitResults);
    }


    @Test
    public void testSaveWorkInfo(){
        workService.saveWorkInfo();
        workService.saveWorkInfo(12);
        workService.saveWorkInfo(10);
        workService.saveWorkInfo(11);
        workService.saveWorkInfo(9);
        workService.saveWorkInfo(8);
        workService.saveWorkInfo(7);
    }

    @Test
    public void testGetWorkInfo(){
        workService.getWorkInfo("Septemberwh");
    }


    @Test
    public void testGetPeriodOfGitRet(){
        GitResult gitResult = gitResultRepository.findByIssueId(142304643);
       Period[] periodsArr =  workService.getPeriodOfGitRet(gitResult, periods);
        System.out.println(periodsArr.toString());
        GitResult gitResult1 = gitResultRepository.findByIssueId(140625278);
        Period[] periodsArr1 =  workService.getPeriodOfGitRet(gitResult1, periods);
        System.out.println(periodsArr1.toString());
    }

    @Test
    public void testGetPeriodsByNow(){
        List<Period> periods = PeriodFactory.generatePeriodList();
        List<Period> periods1 = workService.getPeriodsByEnd(new Date(), periods);
        System.out.println(periods1.size());
    }

    @Test
    public void testGetWorkHoursByNow(){
        List<GitResult> gitResults1 = workService.getAllGitRetOfWeek("Septemberwh",2016,15);
        List<Period> periods = PeriodFactory.generatePeriodList();
        List<Period> periods1 = workService.getPeriodsByEnd(new Date(), periods);
        int hours = workService.getHoursInWork(gitResults1,periods1);
        Assert.assertTrue(hours>0);
    }

    @Test
    public void testGetWorkInfoOfWeeks(){
        List<GitResult> gitResults1 = workService.getAllGitRetOfWeek("ThomasChant",2016,8);
        int n = workService.getHoursInWork(gitResults1, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults2 = workService.getAllGitRetOfWeek("jefferyxq", 2016, 8);
        int n1 = workService.getHoursInWork(gitResults2, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults3 = workService.getAllGitRetOfWeek("db5433", 2016, 8);
        int n2 = workService.getHoursInWork(gitResults3, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults4 = workService.getAllGitRetOfWeek("hanqiuwan", 2016, 8);//err id=372 ok
        int n3 = workService.getHoursInWork(gitResults4, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults5 = workService.getAllGitRetOfWeek("jiangli0508", 2016, 8);
        int n4 = workService.getHoursInWork(gitResults5, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults6 = workService.getAllGitRetOfWeek("TeemolSparrow", 2016, 8);//err id=378 ok
        int n5 = workService.getHoursInWork(gitResults6, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults7 = workService.getAllGitRetOfWeek("tiandashen", 2016, 8); //id=30
        int n6 = workService.getHoursInWork(gitResults7, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults8 = workService.getAllGitRetOfWeek("bettermouse", 2016, 8);
        int n7 = workService.getHoursInWork(gitResults8, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults9 = workService.getAllGitRetOfWeek("wyang181300", 2016, 8);//err id=84 id=85 id=135 id=134 id=136 id=138
        int n8 = workService.getHoursInWork(gitResults9, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults10 = workService.getAllGitRetOfWeek("WarringCe", 2016, 8);
        int n9 = workService.getHoursInWork(gitResults10, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults11 = workService.getAllGitRetOfWeek("xinxinli", 2016, 8);
        int n10 = workService.getHoursInWork(gitResults11, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults12 = workService.getAllGitRetOfWeek("dc0921", 2016, 8);
        int n11 = workService.getHoursInWork(gitResults12, PeriodFactory.generatePeriodList(8));
        List<GitResult> gitResults13 = workService.getAllGitRetOfWeek("mqwangincar", 2016, 8);
        int n12 = workService.getHoursInWork(gitResults13, PeriodFactory.generatePeriodList(8));
    }

}
