package app.jdbc;

import app.data.menu.MenuList;
import app.data.order.ItemInOrder;
import app.data.order.Order;
import app.data.order.OrderList;
import app.data.worker.LoggedWorker;
import app.data.worker.Worker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
