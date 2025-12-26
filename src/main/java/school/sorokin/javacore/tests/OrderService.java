package school.sorokin.javacore.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        Map<String, String> errors = new HashMap<>();
        int id = order.id();
        if (id <= 0) {
            errors.put("id", "id less or equal zero");
        }
        if (orderRepository.saveOrder(order) != id) {
            errors.put("repository", "order with id " + id + " exists");
        }
        if (!errors.isEmpty()) {
            return "Order processing failed";
        }
        return "Order processing successfully";
    }

    public double calculateTotal(Order order) {
        Optional<Order> orderById = orderRepository.getOrderById(order.id());
        if(orderById.isPresent()) {
            return orderById.get().getTotalPrice();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
