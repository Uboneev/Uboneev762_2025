import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int contractorId;
    private Date orderDate;
    private List<OrderItem> items;

    public Order(int id, int contractorId, Date orderDate) {
        this.id = id;
        this.contractorId = contractorId;
        this.orderDate = orderDate;
        this.items = new ArrayList<>();
    }

    // Добавляем товар в заказ
    public void addItem(OrderItem item) {
        for (OrderItem existingItem : items) {
            if (existingItem.getProductId() == item.getProductId()) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    // Удаление товара
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProductId() == productId);
    }

    // Обновление количества товара
    public void updateItemQuantity(int productId, int newQuantity) {
        for (OrderItem item : items) {
            if (item.getProductId() == productId) {
                item.setQuantity(newQuantity);
                break;
            }
        }
    }

    // Метод для учета прихода товаров (увеличение количества)
    public void receiveGoods(int productId, int quantity) {
        for (OrderItem item : items) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Если товара еще нет в заказе, добавим его
        addItem(new OrderItem(productId, quantity));
    }

    public void printOrder() {
        System.out.println("Order ID: " + id);
        System.out.println("Contractor ID: " + contractorId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Items:");
        for (OrderItem item : items) {
            System.out.println(" - Product ID: " + item.getProductId() + ", Quantity: " + item.getQuantity());
        }
    }
}