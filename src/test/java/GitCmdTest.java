import com.incar.gitApi.entity.GitCmd;
import com.incar.gitApi.repository.GitCmdRepository;
import org.junit.*;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19 0019.
 */

public class GitCmdTest {

    private GitCmdRepository gitCmdRepository;

    @Before
    public void setup(){
        GitCmd gitCmd = new GitCmd("curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Rental653/issues?state=all");
        List<GitCmd> gitCmds = Arrays.asList(
                new GitCmd("curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Rental653/issues?state=all"),
                new GitCmd("curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/BriAir/issues?state=all"),
                new GitCmd("curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Triclops/issues?state=all")
        );
        gitCmdRepository = Mockito.mock(GitCmdRepository.class);
        Mockito.when(gitCmdRepository.findAll()).thenReturn(gitCmds);
    }
    @Test
    public void testFindAll(){
        List<GitCmd> gitCmdList = gitCmdRepository.findAll();
        Assert.assertEquals(gitCmdList.size(), 3);
    }
}
