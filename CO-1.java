import java.util.*;

class Order {
    int orderId;
    int price;
    int quantity;

    Order(int orderId, int price, int quantity) {
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderID=" + orderId +
               ", Price=₹" + price +
               ", Qty=" + quantity;
    }
}

public class NSEOrderBook {

    // Price -> Orders (Descending Order)
    private TreeMap<Integer, List<Order>> orderBook =
            new TreeMap<>(Collections.reverseOrder());

    // OrderID -> Order
    private HashMap<Integer, Order> orderMap = new HashMap<>();

    // Match threshold
    private static final int MATCH_PRICE = 2988;

    // Insert Order
    public void insertOrder(Order order) {

        // Immediate Match
        if (order.price >= MATCH_PRICE) {
            System.out.println("Matched Immediately : " + order);
            return;
        }

        orderBook.putIfAbsent(order.price, new ArrayList<>());
        orderBook.get(order.price).add(order);

        orderMap.put(order.orderId, order);

        System.out.println("Inserted : " + order);
    }

    // Cancel by Order ID
    public void cancelOrder(int orderId) {

        Order order = orderMap.get(orderId);

        if (order == null) {
            System.out.println("Order " + orderId + " not found.");
            return;
        }

        List<Order> list = orderBook.get(order.price);
        list.remove(order);

        if (list.isEmpty()) {
            orderBook.remove(order.price);
        }

        orderMap.remove(orderId);

        System.out.println("Cancelled : OrderID=" + orderId);
    }

    // Best Bid
    public void bestBid() {

        if (orderBook.isEmpty()) {
            System.out.println("Order Book Empty");
            return;
        }

        int bestPrice = orderBook.firstKey();

        System.out.println("\nBest Bid Price = ₹" + bestPrice);

        for (Order o : orderBook.get(bestPrice)) {
            System.out.println(o);
        }
    }

    // Display Order Book
    public void displayBook() {

        System.out.println("\n----- BUY ORDER BOOK -----");

        if (orderBook.isEmpty()) {
            System.out.println("Empty");
            return;
        }

        for (Map.Entry<Integer, List<Order>> entry : orderBook.entrySet()) {

            System.out.println("Price ₹" + entry.getKey());

            for (Order o : entry.getValue()) {
                System.out.println("   " + o);
            }
        }
    }

    public static void main(String[] args) {

        NSEOrderBook book = new NSEOrderBook();

        // Incoming Orders
        book.insertOrder(new Order(101, 2985, 100));
        book.insertOrder(new Order(102, 2990, 200));
        book.insertOrder(new Order(103, 2978, 150));
        book.insertOrder(new Order(104, 2987, 120));
        book.insertOrder(new Order(105, 2989, 80));
        book.insertOrder(new Order(106, 2975, 250));
        book.insertOrder(new Order(107, 2986, 300));

        book.displayBook();

        book.bestBid();

        System.out.println();

        // Cancel Orders
        book.cancelOrder(104);
        book.cancelOrder(106);

        book.displayBook();

        book.bestBid();
    }
}