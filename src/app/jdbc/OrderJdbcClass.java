package app.jdbc;

import app.data.order.ItemInOrder;
import app.data.order.Order;
import app.data.order.OrderList;
import app.data.worker.LoggedWorker;
import app.data.worker.Worker;

import java.sql.*;
import java.util.ArrayList;

public class OrderJdbcClass {
    private static OrderJdbcClass instance = new OrderJdbcClass();
    public static OrderJdbcClass getInstance(){return instance;}
    
    private OrderJdbcClass(){}

    public ArrayList<Order> getOrdersFromDatabase(boolean unclaimedOrders, boolean claimedOrdersCheckBoxSelected, boolean myOrdersCheckBoxSelected) {
        Statement stmt = null;
        String query = "select * from zamowiony_towar";
        if(!unclaimedOrders && !claimedOrdersCheckBoxSelected && !myOrdersCheckBoxSelected)
            return new ArrayList<Order>();
        if(unclaimedOrders && claimedOrdersCheckBoxSelected && ! myOrdersCheckBoxSelected)
            query = "select * from zamowiony_towar";
        else if(myOrdersCheckBoxSelected && unclaimedOrders && claimedOrdersCheckBoxSelected)
            query = "select * from zamowiony_towar where menedzer_id_roli = " + LoggedWorker.getInstance().getId_prac();
        else if(myOrdersCheckBoxSelected){
            if (unclaimedOrders) {
                query = "select * from zamowiony_towar where menedzer_id_roli = " + LoggedWorker.getInstance().getId_prac() +
                         "and czy_dostarczony = 'F'";
            }else if (claimedOrdersCheckBoxSelected) {
                query = "select * from zamowiony_towar where menedzer_id_roli = " + LoggedWorker.getInstance().getId_prac() +
                        "and czy_dostarczony = 'T'";
            }
        } else {
            if(unclaimedOrders)
                query = "select * from zamowiony_towar where czy_dostarczony = 'F'";
            else if (claimedOrdersCheckBoxSelected)
                query = "select * from zamowiony_towar where czy_dostarczony = 'T'";
        }
        ArrayList<Order> orders = new ArrayList<>();
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
//            OrderList.getInstance().setOrderList(new ArrayList<>());
            while(rs.next()) {
                int managerId = LoggedWorker.getInstance().getId_prac();
                Worker manager = WorkerJdbcClass.getInstance().getWorkerById(managerId);
                Order order = new Order(managerId, rs.getInt(2), rs.getString(3), manager.getName() + " " + manager.getSurname());
//                OrderList.getInstance().addOrder(order);
                orders.add(order);
            }
//            OrderList.getInstance().setDownloadedData(true);
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
        return orders;
    }

    public ArrayList<ItemInOrder> getProductsInOrdersFromDatabase(Integer valueOf) {
        Statement stmt = null;
        String query = "select * from towar_na_zamowieniu where towar_id_rachunku = " + valueOf;
        ArrayList<ItemInOrder> items = new ArrayList<>();
        try {
            stmt = JdbcConnector.getInstance().getConn().createStatement();
            ResultSet rs = stmt.executeQuery(query);
//            OrderList.getInstance().getOrder(valueOf).setProducts(new ArrayList<>());
            while(rs.next()) {
                ItemInOrder item = new ItemInOrder(rs.getString(3), rs.getInt(2), rs.getInt(1));
//                OrderList.getInstance().getOrder(valueOf).addItemToList(item);
                items.add(item);
            }
//            OrderList.getInstance().getOrder(valueOf).setDownloadedData(true);
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

    public void claimOrder(int orderId) {
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

    public void deleteItemFromOrder(Integer id, String name) {
        CallableStatement stmt = null;
        String query = "{CALL towar_na_zamowieniu_functions.usun_towar_z_zamowienia(?, ?)}";
        try {
            stmt = JdbcConnector.getInstance().getConn().prepareCall(query);
            stmt.setInt(1, id);
            stmt.setString(2, name);
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

    public Order getOrder(Integer valueOf) {
        Order order = null;
        return order;
    }
}
