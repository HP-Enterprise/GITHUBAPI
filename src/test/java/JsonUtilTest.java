import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.jsonObj.PullRequest;
import com.incar.gitApi.jsonObj.User;
import com.incar.gitApi.query.IssueQuery;
import com.incar.gitApi.util.JsonUtils;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ct on 2016/2/21 0021.
 */

@SuppressWarnings("unchecked")
public class JsonUtilTest {

        @Test
        public void TestQueryMultArray() throws Exception{
                IssueQuery issueQuery = new IssueQuery();
                Connection connection = issueQuery.conn("121.40.157.200", "deploy", "deploy");
                Session session = connection.openSession();
                InputStreamReader inputStreamReader = issueQuery.executeCmd("curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Rental653/issues?state=all", session);
                Long begin = System.currentTimeMillis();
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                List<Map> maps = (List<Map>)JsonUtils.readValue(bufferedReader,Object.class);
                Long end = System.currentTimeMillis();
                System.out.println("耗时："+(end-begin));

                List<Issue> issues = new ArrayList<>();
                for(int i=0;i<maps.size();i++){
                        System.out.println(maps.get(i));
                        String jsonstr = JsonUtils.toJson(maps.get(i));
                        Issue issue = JsonUtils.toObject(jsonstr,Issue.class);
                        issues.add(issue);
                }
                Assert.assertTrue(issues.size()>0);
        }
}
