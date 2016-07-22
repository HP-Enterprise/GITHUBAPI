import com.incar.gitApi.Application;
import com.incar.gitApi.GithubClientConfig;
import com.incar.gitApi.service.RepoService;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Repository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.ArrayList;
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
    private GithubClientConfig githubClientConfig;

    @Test
    public void testRepositoryService() {
        Repository repository = new Repository();
        repository.setName("newProject");
        repository.setDescription(" ");
        repository.setPrivate(false);
        Repository se = repoService.addRepository(repository);
        System.out.println(se.getSize());
        System.out.println(se);

    }
    @Test
    public void testCreateIssue(){
        Issue issue= new Issue();
       issue.setTitle("kan�中号");
        List<Label> list=new ArrayList<>();
        Label la=new Label();
        la.setName("H1");
        la.setName("D2");
         list.add(la);
        issue.setLabels(list);
        String rep="MyProject";
        String name=githubClientConfig.getUsername();
        Issue issue1= null;
        try {
            issue1 = repoService.addIssue(name, rep, issue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(issue1);
    }
}
