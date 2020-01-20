package app.jdbc;

import app.data.magazine.MagazineItem;
import app.data.magazine.MagazineList;

import java.sql.*;
import java.util.ArrayList;

public class MagazineJdbcClass {
    private static MagazineJdbcClass instance = new MagazineJdbcClass();
    
    private MagazineJdbcClass(){}
    
    public static MagazineJdbcClass getInstance() {
        return instance;
    }

    public void getItems() {
        Statement stmt = null;
        String query = "select * from magazyn";
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            MagazineList.getInstance().setItemsInMagazine(new ArrayList<>());
            while(rs.next()) {
                MagazineItem item = new MagazineItem(rs.getInt(2), rs.getString(1));
                MagazineList.getInstance().addItem(item);
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
    }

    public void addItem(MagazineItem newItem) {
        CallableStatement stmt = null;
        String query = "{CALL magazyn_functions.nowy_towar(?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setString(1, newItem.getName());
            stmt.setInt(2, newItem.getAmount());
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

    public void takeItem(String name, int amount) {
    }
}
