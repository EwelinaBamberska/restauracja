package app.data.order;

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

    public List<Order> getOrderList() {
        return orderList;
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

    public void removePosition(String name) {
        Order positionToDelete = null;
        for (Order p:
                orderList) {
//            if(name.equals(p.getName())) {
//                positionToDelete = p;
//                break;
//            }
        }
        orderList.remove(positionToDelete);
    }

    public Order getOrder(Integer id) {
        for (Order o:
                orderList){
            if (o.getManagerId() == id)
                return o;
        }
        return null;
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }
}
