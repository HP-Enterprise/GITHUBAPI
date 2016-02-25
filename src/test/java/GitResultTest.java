import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.service.GitResultService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ct on 2016/2/21 0021.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class GitResultTest {

    @Autowired
    private GitResultService gitResultService;

    @Autowired
    private GitResultRepository gitResultRepository;

    @Test
    @Transactional
    public void testSaveGitResult(){
        List<Issue> issues = gitResultService.queryGitApi();
        Set<GitResult> gitResults = gitResultService.issuesToGitResults(issues);
        gitResultService.saveGitResult(gitResults);
    }

}
