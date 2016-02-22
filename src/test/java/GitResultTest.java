import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.service.GitResultService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by Administrator on 2016/2/21 0021.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class GitResultTest {

    @Autowired
    private GitResultService gitResultService;

    @Test
    public void testQueryApi(){
        List<GitResult> gitResults = gitResultService.queryGitApi();
        System.out.println(gitResults.size());
    }
}
