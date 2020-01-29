package app.jdbc;

import app.data.bill.Bill;
import app.data.bill.DishInBill;
import app.data.worker.LoggedWorker;

import java.sql.*;
import java.util.ArrayList;

public class BillJdbcClass {
    private static BillJdbcClass instance = new BillJdbcClass();
    public static BillJdbcClass getInstance(){
        return instance;
    }

    private BillJdbcClass(){
    }

    public ArrayList<Bill> getBills(boolean toPay, boolean paidCheckBoxSelected, boolean onlyManagerCheckBoxSelected) {
        ArrayList<Bill> bills = new ArrayList<>();
        String query = "select * from rachunek";
        Statement stmt = null;

        if(!toPay && !paidCheckBoxSelected && !onlyManagerCheckBoxSelected)
            return new ArrayList<>();
        if(onlyManagerCheckBoxSelected){
            if(toPay && paidCheckBoxSelected || !toPay && !paidCheckBoxSelected)
                query = "select * from rachunek where id_pracownika = " + LoggedWorker.getInstance().getId_prac();
            else if (toPay)
                query = "select * from rachunek where id_pracownika = " + LoggedWorker.getInstance().getId_prac()
                        + " and oplacono = 'F'";
            else query = "select * from rachunek where id_pracownika = " + LoggedWorker.getInstance().getId_prac()
                    + " and oplacono = 'T'";
        }
        else {
            if(toPay && paidCheckBoxSelected)
                query = "select * from rachunek";
            else if (toPay)
                query = "select * from rachunek where oplacono = 'F'";
            else
                query = "select * from rachunek where oplacono = 'T'";
        }
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                Bill bill = new Bill(rs.getInt(1), rs.getInt(2), rs.getFloat(3),
                        rs.getInt(4), rs.getInt(5), rs.getDate(6), rs.getString(7));
                bills.add(bill);
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
        return bills;
    }

    public Bill getBillByID(Integer valueOf) {
        Bill bill = null;
        Statement stmt = null;
        String query = "select * from rachunek where id_rachunku = " + valueOf;
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                bill = new Bill(rs.getInt(1), rs.getInt(2), rs.getFloat(3),
                        rs.getInt(4), rs.getInt(5), rs.getDate(6), rs.getString(7));
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
        return bill;
    }

    public void payByBill(int billId, int rate) {
        CallableStatement stmt = null;
        String query = "{CALL rachunek_functions.oplac_rachunek(?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, billId);
            stmt.setInt(2, rate);
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
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

    public ArrayList<DishInBill> getItemsOnBill(Integer valueOf) {
        Statement stmt = null;
        String query = "select * from danie_na_zamowieniu where rachunek_id_rachunku = " + valueOf;
        ArrayList<DishInBill> items = new ArrayList<>();
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                DishInBill item = new DishInBill(rs.getInt(1), rs.getInt(2), rs.getString(3));
                items.add(item);
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
        return items;
    }

    public void deleteDishFromBill(Integer id, String name) {
        CallableStatement stmt = null;
        String query = "{CALL danie_na_zamowieniu_functions.usun_danie_z_rachunku(?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(2, id);
            stmt.setString(1, name);
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
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

    public void addDishToBill(DishInBill item) {
        CallableStatement stmt = null;
        String query = "{CALL danie_na_zamowieniu_functions.dodaj_danie(?, ?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, item.getBillId());
            stmt.setInt(2, item.getAmount());
            stmt.setString(3, item.getItemName());
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
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

    public void createBill(String TableNumber){
        CallableStatement stmt = null;
        String query = "INSERT INTO RACHUNEK VALUES (ID_RACHUNKU_SEQ.NEXTVAL, NULL, 0, "+ TableNumber +", " + LoggedWorker.getInstance().getId_prac() + ", current_date, 'F')";
        try{
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
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

    public boolean checkDishName(String name){
        PreparedStatement stmt;
        String query = "SELECT NAZWA_DANIA FROM MENU WHERE NAZWA_DANIA =?";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareStatement(query);
            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return false;
            }else
                return true;
        }catch (SQLException e){
            throw new Error("Problem", e);
        }
    }

}
