package school.sorokin.javacore.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderRepository {
    private final Map<Integer, Order> orderStorage = new HashMap<>();

    public int saveOrder(Order order) {
        int id = order.id();
        if (orderStorage.containsKey(id)) return -1;
        orderStorage.put(id, order);
        return id;
    }

    public Optional<Order> getOrderById(int id) {
        return orderStorage.containsKey(id) ?
                Optional.of(orderStorage.get(id)) :
                Optional.empty();
    }
}
