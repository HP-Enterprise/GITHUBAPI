import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.incar.entity.GitCmd;
import com.incar.query.IssueQuery;
import com.incar.repository.GitCmdRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19 0019.
 */
public class IssueQueryTest {

    private String hostname;

    private String username;

    private String password;

    @Value("${com.incar.server.hostname}")
    public void setHostname(){};

    @Value("${com.incar.server.username}")
    public void setUsername(){};

    @Value("${com.incar.server.password}")
    public void setPassword(){}


    @Autowired
    private IssueQuery issueQuery;

    private  Connection connection;

    private String cmd = "curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Rental653/issues?state=all";

    @Before
    public void setup(){
//        Connection connection = issueQuery.conn(hostname,username,password);
    }

    @Test
    public void testExecuteCmd() throws Exception{
        Session session = connection.openSession();
        InputStreamReader inputStreamReader = issueQuery.executeCmd(cmd, session);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = null;
        try {
            while (true){
                line = br.readLine();
                System.out.println(line);
                if(line==null) break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
