package com.incar.query;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/2/19 0019.
 */
public interface Query {
     Connection conn(String hostname,String username,String password);

     InputStreamReader executeCmd(String command,Session session);

     Session getSession(Connection connection);
}
