package app.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkerJdbcClass {
    Statement stmt = null;

    private static WorkerJdbcClass instance = new WorkerJdbcClass();

    public static WorkerJdbcClass getInstance() {
        return instance;
    }

    public boolean findIfWorkerExist(int id) {
        String query = "select * from pracownik where id_prac =" + id;
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }
}
