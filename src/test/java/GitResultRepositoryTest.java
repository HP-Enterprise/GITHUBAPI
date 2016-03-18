import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.util.DateUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class GitResultRepositoryTest {
    private GitResultRepository gitResultRepository;

    @Autowired
    public void setGitResultRepository(GitResultRepository gitResultRepository){
        this.gitResultRepository = gitResultRepository;
    }

    @Test
    public void testFindAllAssignee(){
        List<String> assignees = gitResultRepository.findAllAssignee();
        Assert.notEmpty(assignees);
        System.out.println(assignees.toString());
    }

    @Test
    public void testFindClosedIssue() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<GitResult> gitResults = gitResultRepository.findClosedIssue("Septemberwh","closed",sdf.parse( "2016-03-14"),sdf.parse("2016-03-18"));
        List<GitResult> gitResults1 = gitResultRepository.findClosedIssue("wangh2015","closed",sdf.parse( "2016-03-14"),sdf.parse("2016-03-18"));

        System.out.println("gitResults.size"+gitResults.size());
        System.out.println(gitResults.toString());
        System.out.println("gitResults1.size"+gitResults1.size());
        System.out.println(gitResults1.toString());
        Assert.notEmpty(gitResults1);
        Assert.notEmpty(gitResults);
    }

    @Test
    public void testFindOpenIssue(){
        List<GitResult> gitResults = gitResultRepository.findOpenIssue("wangh2015","open");
        System.out.println("gitRet size"+gitResults.size());
        System.out.println(gitResults.toString());
    }
}
