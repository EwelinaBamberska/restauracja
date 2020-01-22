package app.jdbc;

import app.data.menu.MenuList;
import app.data.order.ItemInOrder;
import app.data.order.Order;
import app.data.order.OrderList;
import app.data.worker.LoggedWorker;
import app.data.worker.Worker;

import java.sql.*;

public class OrderJdbcClass {
    private static OrderJdbcClass instance = new OrderJdbcClass();
    public static OrderJdbcClass getInstance(){return instance;}
    
    private OrderJdbcClass(){}

    public void getOrdersFromDatabase() {
        Statement stmt = null;
        String query = "select * from zamowiony_towar";
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                int managerId = rs.getInt(1);
                Worker manager = WorkerJdbcClass.getInstance().getWorkerById(managerId);
                Order order = new Order(managerId, rs.getInt(2), rs.getString(3), manager.getName() + " " + manager.getSurname());
                OrderList.getInstance().addOrder(order);
            }
            OrderList.getInstance().setDownloadedData(true);
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

    public void getProductsInOrdersFromDatabase(Integer valueOf) {
        Statement stmt = null;
        String query = "select * from towar_na_zamowieniu where towar_id_rachunku = " + valueOf;
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                ItemInOrder item = new ItemInOrder(rs.getString(3), rs.getInt(2), rs.getInt(1));
                OrderList.getInstance().getOrder(valueOf).addItemToList(item);
            }
            OrderList.getInstance().getOrder(valueOf).setDownloadedData(true);
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
    
    private int getNextValOrderSeq(){
        String sql = "select id_zamowienia_seq.nextval from DUAL";
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
    
    public int createOrder(){
        CallableStatement stmt = null;
        String query = "{CALL menedzer_functions.zamow_towar(?, ?)}";
        try {
            int orderId = getNextValOrderSeq();
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, LoggedWorker.getInstance().getId_prac());
            stmt.setInt(2, orderId);
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
            return orderId;
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Nie można zamówić towaru, którego nie ma w magazynie.");
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

    public void addItemInOrder(ItemInOrder item) {
        CallableStatement stmt = null;
        String query = "{CALL towar_na_zamowieniu_functions.dodaj_towar(?, ?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getAmountOfProduct());
            stmt.setString(3, item.getName());
            stmt.executeQuery();
            JdbcConnector.getInstance().getConn().commit();
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Nie można zamówić towaru, którego nie ma w magazynie.");
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

    public void claimOrder(String orderId) {
        CallableStatement stmt = null;
        String query = "{CALL menedzer_functions.odbierz_towar(?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, Integer.valueOf(orderId));
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
