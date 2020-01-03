package app.jdbc;

import app.data.LoggedWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WorkerJdbcClass {
    private Statement stmt = null;

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
                LoggedWorker worker = new LoggedWorker(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6), rs.getString(7));
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
