import com.incar.gitApi.Application;
import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.service.*;
import com.incar.gitApi.util.DateUtil;
import org.eclipse.egit.github.core.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class ReposTest {


    @Autowired
    private RepoService repoService;

    @Autowired
    private MyLabelService myLabelService;
    @Autowired
    private MyMilestoneService myMilestoneService;
    @Autowired
    private MyOrgService myOrgService;
    @Autowired
    private GithubClientConfig githubClientConfig;

    @Test
    public void testRepositoryService() {
        Repository repository = new Repository();
        repository.setName("newqwe");
        repository.setDescription("fgdgfgfg");
        repository.setHasIssues(true);
        repository.setHasDownloads(true);
        repository.setPrivate(false);
        Repository se = repoService.addRepository(repository);
        System.out.println(se.getSize());
        System.out.println(se);
    }

    @Test
    public void testLabel() throws IOException {

        String rep = "RentalBA";
        myLabelService.getAllLabel("HP-Enterprise", rep);
        System.out.println(myLabelService.getAllLabel("HP-Enterprise", rep).toString());
    }

    @Test
    public void testMilestone() throws IOException {
        String rep = "RentalBA ";
        List<Milestone> list = myMilestoneService.getAllMiles(githubClientConfig.getUsername(), rep, "open");
        System.out.println(list.toString());

    }

    @Test
    public void testAllMilestone() throws IOException {

        myMilestoneService.addAllMilestone("HP-Enterprise", "MyProject", 2016);

    }
    @Test
    public void testRepository()throws  IOException{
   List<Repository>  repository=   repoService.getRepository();
       Repository repository1=   repository.get(2);
        System.out.println(repository1.getId());
        System.out.println(repository1.getName());
        System.out.println(repository1.getOwner());
    }
    @Test
    public void testEditRepository()throws IOException{
        Repository repository=new Repository();
        repository.setName("Myproject");
        repository.setDescription("这是接口测试");

        repoService.editRepository("HP-Enterprise", "MyProject", repository);

    }


}
