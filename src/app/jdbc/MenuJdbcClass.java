package app.jdbc;

import app.data.menu.MenuList;
import app.data.menu.MenuPosition;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuJdbcClass {
    private static MenuJdbcClass menuJdbcClass = new MenuJdbcClass();
    public static MenuJdbcClass getInstance() {
        return menuJdbcClass;
    }


    public ArrayList<MenuPosition> getMenuItemsFromDatabase() {
        Statement stmt = null;
        String query = "select * from menu";
        ArrayList<MenuPosition> itemsInDB = new ArrayList<>();
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                MenuPosition position = new MenuPosition(rs.getString(1), rs.getDouble(2));
//                MenuList.getInstance().addMenuPosition(position);
                itemsInDB.add(position);
            }
//            MenuList.getInstance().setDownloadedData(true);
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
        return itemsInDB;
    }

    public void addMenuPosition(MenuPosition position){
        CallableStatement stmt = null;
        String query = "{CALL menu_functions.dodaj_danie(?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setString(1, position.getName());
            stmt.setFloat(2, Float.parseFloat(String.valueOf(position.getPrice())));
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

    public void deleteMenuPosition(String position){
        CallableStatement stmt = null;
        String query = "{CALL menu_functions.usun_danie(?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setString(1, position);
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

    public void modifyPrice(String name, Double newPrice) {
        CallableStatement stmt = null;
        String query = "{CALL menu_functions.zmien_cene(?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setString(1, name);
            stmt.setFloat(2, Float.parseFloat(String.valueOf(newPrice)));
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
}
