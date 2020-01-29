package app.jdbc;

import app.data.magazine.MagazineItem;

import java.sql.*;
import java.util.ArrayList;

public class MagazineJdbcClass {
    private static MagazineJdbcClass instance = new MagazineJdbcClass();
    
    private MagazineJdbcClass(){}
    
    public static MagazineJdbcClass getInstance() {
        return instance;
    }

    public ArrayList<MagazineItem> getItems() {
        Statement stmt = null;
        String query = "select * from magazyn";
        ArrayList<MagazineItem> items = new ArrayList<>();
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
//            MagazineList.getInstance().setItemsInMagazine(new ArrayList<>());
            while(rs.next()) {
                MagazineItem item = new MagazineItem(rs.getInt(2), rs.getString(1));
//                MagazineList.getInstance().addItem(item);
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

    public void addItem(MagazineItem newItem) {
        CallableStatement stmt = null;
        String query= "{CALL magazyn_functions.insert_item(?, ?)}";
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
        CallableStatement stmt = null;
        String query = "{CALL magazyn_functions.usun_towar(?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setString(1, name);
            stmt.setInt(2, amount);
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

    public int getAmountOfItem(String name) {
        PreparedStatement stmt = null;
        String query = "select ilosc from magazyn where nazwa_towaru = ?";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                return rs.getInt(1);
            }
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
        return -1;
    }

    public void deleteItemFromMagazine(String name) {
        CallableStatement stmt = null;
        String query = "{CALL magazyn_functions.delete_item_name(?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
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

    public boolean checkIfProductExists(String name) {
        PreparedStatement stmt = null;
        String query = "select * from magazyn where nazwa_towaru = ?";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                return true;
            }
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
        return false;
    }
}
