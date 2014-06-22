package main.sitronics.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleDriver;


public class DBConnection {
    public static final String JNDIPath = "jdbc/rtk-csv";
    public static final String JNDIEnv = "java:comp/env";
    private static final String DBSource = "jdbc:oracle:thin:@192.168.2.165:1521/DWHQA";
    private static final String Username = "LOGISTICS";
    private static final String Password = "LOGISTICS";

    public DBConnection() {
        super();
    }

    /**
     * Get Direct Connection from database using username and password
     * those parameters are in DBSource, Username, Password
     * it's inappropriate to use just direst connection; it's built for test
     *
     * @return
     */
    public static Connection getDirectConnection() {
        Connection connection = null;
        try {
            DriverManager.registerDriver(new OracleDriver());
            connection =
                    DriverManager.getConnection(DBSource, Username, Password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnetion() throws NamingException,
                                                   SQLException {
        //driver class name: oracle.jdbc.xa.client.OracleXADataSource
        Context envContext = (Context)(new InitialContext()).lookup(JNDIEnv);
        DataSource dtSrc = (DataSource)envContext.lookup(JNDIPath);

        return dtSrc.getConnection();
    }

    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnetion();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
