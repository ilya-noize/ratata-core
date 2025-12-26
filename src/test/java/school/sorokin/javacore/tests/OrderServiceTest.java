package school.sorokin.javacore.tests;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    private final OrderRepository mockRepository = mock(OrderRepository.class);
    private final OrderService orderService = new OrderService(mockRepository);

    @Test
    void processOrderSuccess() {
        Order order = new Order(1, "product", 20, 1.0);
        when(mockRepository.saveOrder(order)).thenReturn(1);
        assertEquals("Order processing successfully", orderService.processOrder(order));
        verify(mockRepository, times(1)).saveOrder(order);
    }

    @Test
    void processOrderFailWhenIdLessZero() {
        Order order = new Order(0, "product", 20, 1.0);
        when(mockRepository.saveOrder(order)).thenReturn(-1);
        assertEquals("Order processing failed", orderService.processOrder(order));
        verify(mockRepository, times(1)).saveOrder(order);
    }

    @Test
    void calculateTotalThrowWhenOrderNotFound(){
        Order order = new Order(10, "product", 20, 1.0);
        when(mockRepository.getOrderById(order.id())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateTotal(order));
        verify(mockRepository, times(1)).getOrderById(order.id());
    }
    @Test
    void calculateTotalFailWhenQuantityEqualsZero() {
        Order order = new Order(0, "product", 0, 1.0);
        when(mockRepository.getOrderById(order.id())).thenReturn(Optional.of(order));
        assertEquals(0,orderService.calculateTotal(order));
        verify(mockRepository, times(1)).getOrderById(order.id());
    }

    @Test
    void calculateTotalFailWhenQuantityLessZero() {
        Order order = new Order(0, "product", -1, 1.0);
        when(mockRepository.getOrderById(order.id())).thenReturn(Optional.of(order));
        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateTotal(order),
                "Exception if quantity < 0");
        verify(mockRepository, times(1)).getOrderById(order.id());
    }

    @Test
    void calculateTotalFailUnitPriceEqualsZero() {
        Order order = new Order(0, "product", 1, 0.0);
        when(mockRepository.getOrderById(order.id())).thenReturn(Optional.of(order));
        assertEquals(0,orderService.calculateTotal(order));
        verify(mockRepository, times(1)).getOrderById(order.id());
    }

    @Test
    void calculateTotalFailUnitPriceLessZero() {
        Order order = new Order(0, "product", 1, -0.1);
        when(mockRepository.getOrderById(order.id())).thenReturn(Optional.of(order));
        assertThrows(IllegalArgumentException.class,
                () -> orderService.calculateTotal(order),
                "Exception if unitPrice < 0");
        verify(mockRepository, times(1)).getOrderById(order.id());
    }
}