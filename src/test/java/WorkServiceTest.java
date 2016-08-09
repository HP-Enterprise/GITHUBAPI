import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.Work;
import com.incar.gitApi.period.Period;
import com.incar.gitApi.period.PeriodFactory;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.service.WorkService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/20 0020.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class WorkServiceTest {
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkService workService;

    private SimpleDateFormat sdf;


    @Before
    public void setup(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    }


    @Test
    public void testIsInThisWeek() throws ParseException {
        Date date = sdf.parse("2016-03-26 16-00-00");
        boolean isIn = workService.isBeforeThisWeek(date);
        System.out.println("isIn ?"+isIn);
    }


    @Test
    public void testFindOpenGitRet() throws ParseException {
//        String assgnee = "Septemberwh";
//        List<GitResult> gitResults = workService.getOpenGitRet(assgnee, sdf.parse("2016-03-27 00-00-00"));
//        System.out.println("testFindOpenGitRet()"+gitResults);
    }

    @Test
    public void testFindClosedGitRet() throws ParseException {
        String assgnee = "Gupeng133";
        List<GitResult> gitResults = workService.getClosedGitRet(assgnee, sdf.parse("2016-03-20 00-00-00"), sdf.parse("2016-03-26 23-59-59"));
        System.out.println(gitResults);
    }


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


    @Test
    public void testGetPeriodsByNow(){
        List<Period> periods = Arrays.asList(PeriodFactory.generatePeriods());
       // List<Period> periods1 = workService.getPeriodsByEnd(new Date(), periods);
   //     System.out.println(periods1.size());
    }


    @Test
    public void testGetWorkInfoOfWeeks(){
        Work work1 = workService.getWorkInfo("TeemolSparrow",1);
        Work work2 = workService.getWorkInfo("TeemolSparrow",2);
        Work work3 = workService.getWorkInfo("TeemolSparrow",3);
        Work work4 = workService.getWorkInfo("TeemolSparrow",4);
        Work work5 = workService.getWorkInfo("TeemolSparrow",5);
        Work work6 = workService.getWorkInfo("TeemolSparrow",6);
        Work work7 = workService.getWorkInfo("TeemolSparrow",7);
        Work work8 = workService.getWorkInfo("TeemolSparrow",7);
        Work work9 = workService.getWorkInfo("TeemolSparrow",9);
        Work work10 = workService.getWorkInfo("TeemolSparrow",10);
        Work work11= workService.getWorkInfo("TeemolSparrow",11);
        Work work12= workService.getWorkInfo("TeemolSparrow",12);
        Work work13= workService.getWorkInfo("TeemolSparrow",13);
        Work work14= workService.getWorkInfo("TeemolSparrow",14);
        Work work15 = workService.getWorkInfo("wyang181300", 15);
        Work work16 = workService.getWorkInfo("mqwangincar", 16);
        Work work17  = workService.getWorkInfo("bettermouse", 17);
        Work work18  = workService.getWorkInfo("TeemolSparrow", 20);
    }
@Test
public void testExcel(){
      HSSFWorkbook workbook= workService.findWorkToExcel(null, null, null);

    System.out.println(workbook.getAllEmbeddedObjects().size()+"kkk");
    System.out.println(workbook.getAllEmbeddedObjects()+"lll");
    System.out.println(workbook.getBytes()+"....");
 //     List<Work> workList= workRepository.findExcel(null, null, null);
//    for (Work work : workList) {
//        work.getId();
//        work.getRealname();
//        work.getUsername();
//        work.getWeekInYear();
//        System.out.printf(work.getUsername());
//        System.out.println(work.getWeekInYear());
//    }
    }


}
