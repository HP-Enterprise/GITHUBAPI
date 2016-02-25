package com.incar.gitApi.query;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.incar.gitApi.entity.GitCmd;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;

/**
 * Created by ct on 2016/2/19 0019.
 */
@Component
public class IssueQuery {

    private String hostname;

    private String username;

    private String password;

    @Value("${com.incar.server.hostname}")
    public void setHostname(String hostname){this.hostname = hostname;};

    @Value("${com.incar.server.username}")
    public void setUsername(String username){this.username = username;};

    @Value("${com.incar.server.password}")
    public void setPassword(String password){this.password = password;}

    private static final int MAX_SESSION_PER_CONN = 5; //设置单个连接所能创建的最大会话数

    public Connection conn(String hostname, String username, String password) {
        Connection conn = new Connection(hostname);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if(isAuthenticated==false) {
                throw new IOException("Authorication failed");
            }
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStreamReader executeCmd(String command, Session session) {
        InputStreamReader isr = null;
        InputStream stdout ;
        try {
            session.execCommand(command);
            stdout = new StreamGobbler(session.getStdout());
            isr = new InputStreamReader(stdout,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isr;
    }

    public Session getSession(Connection connection) {
        Session session;
        try {
            session = connection.openSession();
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return session;
    }


    public List<Issue> executeMultiCmds(List<GitCmd> cmds)  {
        Assert.notEmpty(cmds);
        Connection connection = conn(hostname, username, password);
        Session[] sessions = new Session[cmds.size()];
        List<Issue> issues = new ArrayList<>();
        try {
            for (int i=0;i<cmds.size();i++){
                sessions[i] =  connection.openSession();
                InputStreamReader inputStreamReader = executeCmd(cmds.get(i).getCmd(), sessions[i]);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                issues.addAll(getIssueInfo(bufferedReader));
                if(i > 0 && i % MAX_SESSION_PER_CONN==0){//当会话数超过5个关掉当前连接重新建立
                    connection.close();
                    connection = conn(hostname, username, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            for(Session session : sessions)
            session.close();
            connection.close();
        }
        return issues;
    }


    public List<Issue> getIssueInfo(Reader reader) throws IOException {
        List<Map> issueMaps = (List<Map>)JsonUtils.readValue(reader,Object.class );
        return mapToIssue(issueMaps);
    }

    public void printReader(InputStreamReader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String line ;
        while (true){
            line = br.readLine();
            if(line==null) break;
            else {
                System.out.println(line);
            }
        }
    }

    public static List<Issue> mapToIssue(List<Map> list){
         List<Issue> issues = new ArrayList<>();
         for(Map map : list){
             String jsonStr = JsonUtils.toJson(map);
             Issue issue = JsonUtils.toObject(jsonStr,Issue.class);
             issues.add(issue);
         }
         return issues;
    }
}
