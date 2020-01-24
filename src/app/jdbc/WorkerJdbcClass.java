package app.jdbc;

import app.data.worker.Worker;
import app.data.worker.LoggedWorker;

<<<<<<< HEAD
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
=======
import java.sql.*;
import java.util.ArrayList;
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962

public class WorkerJdbcClass {
    private Statement stmt = null;

    private static WorkerJdbcClass instance = new WorkerJdbcClass();

    public static WorkerJdbcClass getInstance() {
        return instance;
    }

    public boolean logWorker(int id) {
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

    public Worker getWorkerById(int managerId) {
        String query = "select * from pracownik where id_prac =" + managerId;
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()) {
                Worker worker = new Worker(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getDate(4), rs.getString(5),
                        rs.getString(6), rs.getString(7));
                return worker;
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
        return null;
    }

<<<<<<< HEAD
    public void getWorkers() {
=======
    public ArrayList<Worker> getWorkers() {
        Statement stmt = null;
        String query = "select * from pracownik";
        ArrayList<Worker> workers = new ArrayList<>();
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
//            WorkerList.getInstance().setWorkers(new ArrayList<>());
            while(rs.next()) {
                Worker worker = new Worker(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7));
//                WorkerList.getInstance().addWorker(worker);
                workers.add(worker);
            }
//            WorkerList.getInstance().setIfDataDownloaded(true);
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
        return workers;
    }

    public void fireWorker(Worker workerToFire) {
        CallableStatement stmt = null;
        String query = "{CALL menedzer_functions.usun_pracownika(?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, workerToFire.getId_prac());
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Nie można zwolnić pracownika, który nie jest zatrudniony.");
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
    }

    public void modifyWorker(Worker workerToModify) {
        CallableStatement stmt = null;
        String query = "{CALL menedzer_functions.modyfikuj_pracownika(?, ?, ?, ?, ? ,? ,?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, workerToModify.getId_prac());
            stmt.setString(2, workerToModify.getName());
            stmt.setString(3, workerToModify.getSurname());
            stmt.setDate(4, workerToModify.getDate());
            if(workerToModify.isIf_manager()){
                stmt.setString(5, "F");
                stmt.setString(6, "T");
                stmt.setString(7, "F");
            }
            else if(workerToModify.isIf_cooker()){
                stmt.setString(5, "F");
                stmt.setString(6, "F");
                stmt.setString(7, "T");
            }
            else {
                stmt.setString(5, "T");
                stmt.setString(6, "F");
                stmt.setString(7, "F");
            }
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Nie można modyfikować pracownika, który nie jest zatrudniony.");
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
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
    }

    public void fireWorker(Worker workerToShow) {
    }

    public void modifyWorker(Worker workerToShow) {
    }

    public void saveHoursToDB(int id_prac, Date parseToDate, float parseFloat) {
    }
}
