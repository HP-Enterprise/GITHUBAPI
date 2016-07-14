import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.entity.WorkDetail;
import com.incar.gitApi.service.GitResultService;
import com.incar.gitApi.service.WorkDetailService;
import com.incar.gitApi.service.WorkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class WorkDetialServiceTest {
    @Autowired
    private WorkDetailService workDetailService;
    @Autowired
    private GitResultService gitResultService;
    @Autowired
    private WorkService workService;

    @Test
    public void testGetAll() throws ParseException {
        Properties properties = workService.getRealnameProperties();
       List<GitResult> gitResults= gitResultService.findAll();
        List<GitResult> gitResults1= workService.getGitRetHasLabelHOrD(gitResults);
        List<WorkDetail> workDetails =new ArrayList<>();
        for (GitResult gitResult : gitResults1) {
            if(gitResult.getAssignee()!=null) {
                properties.get(gitResult.getAssignee());
                WorkDetail workDetail = new WorkDetail();
                workDetail.setState(gitResult.getState());
                workDetail.setUserName(gitResult.getAssignee());
                workDetail.setState(gitResult.getState());
                workDetail.setTitle(gitResult.getTitle());
                workDetail.setProject(gitResult.getProject());
                workDetail.setExpectedTime(workService.oneIssueWork(gitResult));
                workDetail.setActualTime(workDetailService.oneIssueActuWork(gitResult));
                workDetail.setEfficiency(workDetailService.oneIssueEffic(gitResult));
                System.out.println(properties.get(gitResult.getAssignee()));
                workDetails.add(workDetail);
            }
        }
       System.out.println(workDetails.size());
//        System.out.println(workDetails);

    }

   @Test
    public void testGetWorkDetailIno(){
      List<WorkDetail> workDetails= workDetailService.getWorkDetailInfo();
       System.out.println(workDetails.size());
        System.out.println(workDetails);
  }
    @Test
    @Rollback(false)
    public  void testSave(){
        workDetailService.saveWorkDetailInfo();

    }

    @Test
    public void testPage(){
        String userName= "TeemolSparrow";
        Integer week = 17 ;
     Page<WorkDetail> workDetailPage= workDetailService.findPageOfWorkDetail(userName, 17,2016, 1, 6);
        System.out.println(workDetailPage.getContent());
        System.out.println(workDetailPage.getTotalPages());
        System.out.println(workDetailPage.getTotalElements());
        System.out.println(workDetailPage.getSize());
    }
    @Test
    public void testWorkDetailPage(){
        Page<WorkDetail> workDetailPage = workDetailService.findPageOfWorkDetail("eem", null, null, null, null,2, 2016, null, null);
        System.out.println(workDetailPage.getContent());
        System.out.println(workDetailPage.getTotalPages());
        System.out.println(workDetailPage.getTotalElements());
        System.out.println(workDetailPage.getSize());
    }

}
