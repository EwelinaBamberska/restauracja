package app.jdbc;

import java.sql.*;


public class JdbcConnector {
    private Connection conn = null;
    private static JdbcConnector ourInstance = new JdbcConnector();

    public static JdbcConnector getInstance() {
        return ourInstance;
    }

    private JdbcConnector() { }

    public void connectToDatabase() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//admlab2.cs.put.poznan.pl:1521/"+
                            "dblab02_students.cs.put.poznan.pl", "inf136679", "inf136679");
            System.out.println("Got it!");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
    }

    public void closeConnection(){
        try {
            if (conn != null) {
                conn.close();
                System.out.println("closed.");
            }
        } catch (SQLException ex){
            System.out.println(ex.getErrorCode());
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
