package school.sorokin.javacore.stream;

import java.util.Set;

public record Customer(Long id, String name, Long level, Set<Order> orders) {

}
