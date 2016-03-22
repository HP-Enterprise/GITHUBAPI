import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.util.GithubClientConfig;
import com.incar.gitApi.util.Period;
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

    @Before
    public void setup(){
        this.periods = Period.generatePeriodList();
    }

    @Test
    public void testGetFinishedWorkAmount() throws ParseException {
        System.out.println(githubClientConfig.getPassword());
        System.out.println(githubClientConfig.getRepos().toString());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2016-03-14 00:00:00");
        Date date2 = sdf.parse("2016-03-20 24:00:00");

        int result = workService.getTotalFinishedWork("db5433", date1, date2);
        System.out.println("result:"+result);
    }

    @Test
    public void testIsInThisWeek() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = sdf.parse("2016-03-26 16-00-00");
        boolean isIn = workService.isBeforeThisWeek(date);
        System.out.println("isIn ?"+isIn);
    }

    @Test
    public void testGetUnfinishedWorkOfAssignee(){
        int hours = workService.getUnfinishedWorkOfAssignee("Septemberwh");
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
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
    public void testGetWorkHoursOfAssignee(){
        String assgnee = "wangh2015";
        List<GitResult> gitResults = workService.getAllGitRetThisWeek(assgnee);
        System.out.println("gitResults"+gitResults);
        int n = workService.getHoursInWork(gitResults,periods);
        System.out.println("hour in work of  "+assgnee+" : "+n);
    }


}
