package app.data.order;

import app.data.worker.LoggedWorker;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
    private static OrderList ourInstance = new OrderList();
    public static OrderList getInstance() {
        return ourInstance;
    }

    private List<Order> orderList = new ArrayList<>();
    private boolean downloadedData = false;

    private OrderList() {
    }

    public List<Order> getOrderList(boolean unclaimedOrders, boolean claimedOrdersCheckBoxSelected, boolean myOrdersCheckBoxSelected) {
        List<Order> orders = new ArrayList<>();
        for (Order o:
             orderList) {
            if(myOrdersCheckBoxSelected && o.getManagerId() == LoggedWorker.getInstance().getId_prac()) {
                if (unclaimedOrders && !o.isIfDelivered()) {
                    orders.add(o);
                } else if (claimedOrdersCheckBoxSelected && o.isIfDelivered()) {
                    orders.add(o);
                }
            }
            else {
                if (unclaimedOrders && !o.isIfDelivered()) {
                    orders.add(o);
                } else if (claimedOrdersCheckBoxSelected && o.isIfDelivered()) {
                    orders.add(o);
                }
            }
        }
        return orders;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public boolean isDownloadedData() {
        return downloadedData;
    }

    public void setDownloadedData(boolean downloadedData) {
        this.downloadedData = downloadedData;
    }

    public Order getOrder(Integer id) {
        for (Order o:
                orderList){
            if (o.getOrderId() == id)
                return o;
        }
        return null;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public void claimOrder(int orderId) {
        for (Order o:
             orderList) {
            if (o.getOrderId() == orderId)
                o.setIfDelivered(true);
        }
    }

    public void deleteItemFromOrder(Integer valueOf, String name) {
        for (Order o:
             orderList) {
            if(o.getOrderId() == valueOf){
                o.deleteItem(name);
                break;
            }
        }
    }
}
