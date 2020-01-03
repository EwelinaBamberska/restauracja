package app.jdbc;

import app.data.LoggedWorker;
import app.data.MenuList;
import app.data.MenuPosition;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuJdbcClass {
    private static MenuJdbcClass menuJdbcClass = new MenuJdbcClass();
    public static MenuJdbcClass getInstance() {
        return menuJdbcClass;
    }

    private Statement stmt = null;

    public void getMenuItemsFromDatabase() {
        String query = "select * from menu";
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                MenuPosition position = new MenuPosition(rs.getString(1), rs.getDouble(2));
                MenuList.getInstance().addMenuPosition(position);
            }
            MenuList.getInstance().setDownloadedData(true);
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

    public void addMenuPosition(MenuPosition position){
        String query = "{CALL menu_functions.dodaj_danie(?, ?)}";
        try {
            CallableStatement stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
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
        String query = "{CALL menu_functions.usun_danie(?)}";
        try {
            CallableStatement stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
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
        String query = "{CALL menu_functions.zmien_cene(?, ?)}";
        try {
            CallableStatement stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
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
