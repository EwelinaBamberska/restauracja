package app.jdbc;

import app.data.bill.Bill;
import app.data.worker.LoggedWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
