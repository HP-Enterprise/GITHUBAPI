import com.incar.gitApi.Application;
import com.incar.gitApi.repository.WorkRepository;
import com.incar.gitApi.schedule.ScheduledTask;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.WorkDetailService;
import com.incar.gitApi.service.WorkService;
import com.incar.gitApi.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2016/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class ScheduleTest {
    @Autowired
    private GitResultService gitResultService;
    @Autowired
    private WorkService workService;
    @Autowired
    private WorkDetailService workDetailService;
    @Autowired
    private WorkRepository workRepository;

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Test
    @Rollback(false)
    public void scheduledQuery(){
        logger.info(">>>>>>>>>>> saving gitResult >>>>>>>>>>>>");
        gitResultService.saveGitResult();
    }

    @Test
    @Rollback(false)
    public void gitRetDetail(){
        try {
            Thread.sleep(10000);
            logger.info(">>>>>>>>>>> deleting workDetailInfo >>>>>>>>>>>>");
            workDetailService.deleteWorkDetailInfo();
            logger.info(">>>>>>>>>>> saving workDetailInfo >>>>>>>>>>>>");
            workDetailService.saveWorkDetailInfo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    @Rollback(false)
    public void gitRetAlalyse(){
        try {
            Thread.sleep(10000);
            logger.info(">>>>>>>>>>> deleting workInfo >>>>>>>>>>>>");
            for(int i=1;i<= DateUtil.getWeekInYear();i++) {
                workRepository.deleteByWeek(i);
            }
            logger.info(">>>>>>>>>>> saving workInfo >>>>>>>>>>>>");
            for(int i=1;i<= DateUtil.getWeekInYear();i++){
                workService.saveWorkInfo(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
