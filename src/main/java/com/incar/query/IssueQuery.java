package com.incar.query;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.incar.entity.GitResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19 0019.
 */
@Component
public class IssueQuery implements Query{




    @Override
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
            return null;
        }

    }

    @Override
    public InputStreamReader executeCmd(String command, Session session) {
        InputStreamReader isr = null;
        InputStream stdout ;
        try {
            session.execCommand(command);
            stdout = new StreamGobbler(session.getStdout());
            isr = new InputStreamReader(stdout);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isr;
    }

    @Override
    public Session getSession(Connection connection) {
        Session session = null;
        try {
            session = connection.openSession();
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return session;
    }

//    public GitResult parseIssueInfo(InputStreamReader inputStreamReader){
//        return null;
//    }
//    public List<GitResult> queryAndGetResult(){
//        Connection connection = conn(hostname,username,password);
//
//        return null;
//    }
}
