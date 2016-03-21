import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.util.GithubClientConfig;
import com.incar.gitApi.util.Period;
import org.junit.Assert;
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


    @Test
    public void testGetFinishedWorkAmount() throws ParseException {
        System.out.println(githubClientConfig.getPassword());
        System.out.println(githubClientConfig.getRepos().toString());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2016-03-14 00:00:00");
        Date date2 = sdf.parse("2016-03-20 24:00:00");

        int result = workService.getTotalFinishedWork("db5433",date1,date2);
        System.out.println("result:"+result);
    }

    @Test
    public void testIsInThisWeek() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date date = sdf.parse("2016-03-26 16-00-00");
        boolean isIn = workService.isInThisWeek(date);
        System.out.println("isIn ?"+isIn);
    }

    @Test
    public void testGetUnfinishedWorkOfAssignee(){
        int hours = workService.getUnfinishedWorkOfAssignee("Septemberwh");
        System.out.println("work unfinished:"+hours);
    }

//    @Test
//    public void testGenaratePeriods(){
//        List<Period> periods = workService.generatePeriods();
//        System.out.println(periods.toString());
//        Assert.assertEquals(40, periods.size());
//    }

    @Test
    public void testGetWorkLoadOfOneIssue(){
        List<Period> periods = workService.generatePeriods();
        GitResult gitResult = gitResultRepository.findByIssueId(140075749);
        periods = workService.getWorkPeriodOfOneIssue(gitResult,periods);
        System.out.println("periods="+periods.toString());
        System.out.println("periods size="+periods.size());
    }
}
