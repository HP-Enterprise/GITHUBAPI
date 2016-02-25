import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitCmd;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.query.IssueQuery;
import com.incar.gitApi.repository.GitCmdRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19 0019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class IssueQueryTest {

    private String hostname;

    private String username;

    private String password;

    @Value("${com.incar.server.hostname}")
    public void setHostname(String hostname){this.hostname = hostname;};

    @Value("${com.incar.server.username}")
    public void setUsername(String username){this.username = username;};

    @Value("${com.incar.server.password}")
    public void setPassword(String password){this.password = password;}

    @Autowired
    private IssueQuery issueQuery;

    @Autowired
    private GitCmdRepository gitCmdRepository;

    private  Connection connection;

    private String cmd = "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/githubApi/issues?state=all";

    private String[] cmds =new String[] {
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Rental653/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Triclops/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/SMS/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/DataCenter/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/DataCenter/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/MessageProcessor/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/IncarSelf/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/TcpServer/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Android-BlueTooth/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/JPA-Generator/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/IOS-BlueTooth/issues?state=all",
            "curl -u travis4hpe:travis4Java https://api.github.com/repos/InCar/IncarSelf/issues?state=all"
    };

    @Test
    public void testExecuteCmd() throws Exception{
        Connection connection = issueQuery.conn(hostname,username,password);
        Session session = connection.openSession();
        InputStreamReader inputStreamReader = issueQuery.executeCmd(cmd, session);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line ;
        try {
            while (true){
                line = br.readLine();
                if(line==null) break;
                else {
                    System.out.println(line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryMultiPro() {
        List<GitCmd> gitCmdList = gitCmdRepository.findAll();
        List<Issue> issues = issueQuery.executeMultiCmds(gitCmdList);
        System.out.println( issues.size());
    }
}
