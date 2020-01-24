package app.jdbc;

import app.data.worker.Worker;
import app.data.worker.LoggedWorker;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

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
    }

    public void saveHoursToDB(int id_prac, Date date, float hours, float rate) {
        CallableStatement stmt = null;
        String query = "{CALL menedzer_functions.dodaj_godziny(?, ?, ?, ?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, id_prac);
            stmt.setFloat(2, rate);
            stmt.setInt(3, 1);
            stmt.setDate(5, date);
            stmt.setFloat(4, hours);
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
        }catch (SQLException e) {
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

    private int getNextValOrderSeq(){
        String sql = "select id_prac_seq.nextval from DUAL";
        try {
            PreparedStatement ps = JdbcConnector.getInstance().getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int nextID_from_seq = -1;
            if (rs.next())
                nextID_from_seq = rs.getInt(1);
            return nextID_from_seq;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public int addWorker(String name, String surname, Date date, String waiter, String manager, String cook) {
        CallableStatement stmt = null;
        String query = "{CALL menedzer_functions.dodaj_pracownika(?, ?, ?, ?, ?, ?, ?)}";
        try {
            int workerId = getNextValOrderSeq();
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, workerId);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setDate(4, date);
            stmt.setString(5, waiter);
            stmt.setString(6, manager);
            stmt.setString(7, cook);
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
            return workerId;
        }catch (SQLException e) {
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
}
