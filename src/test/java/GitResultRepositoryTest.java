import com.incar.gitApi.Application;
import com.incar.gitApi.repository.GitResultRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.springframework.util.Assert;

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
        System.out.println(assignees.toString());
    }
}
