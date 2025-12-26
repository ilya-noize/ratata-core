package school.sorokin.javacore.stream;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomerOrdersStarter {

    private static final Random RANDOM = new Random();

    private CustomerOrdersStarter() {
    }

    public static void main(String... args) {
        List<Product> productList = generateProducts();
        List<Customer> customerList = generateCustomers(productList);

        // 1. Список продуктов из категории "Книги и канцелярия" с ценой более 100.
        Predicate<Product> onlyBooks = product -> product.category().equals("Книги и канцелярия");
        Predicate<Product> more100 = product -> product.price().doubleValue() > 100.0d;
        List<Product> task1 = productList.stream()
                .filter(onlyBooks)
                .filter(more100)
                .toList();
        System.out.println("task1 = " + task1);

        // 2. Список заказов с продуктами из категории "Продукты питания".
        Predicate<Product> onlyFood = product -> product.category().equals("Продукты питания");
        List<Product> task2 = productList.stream()
                .filter(onlyFood)
                .toList();
        System.out.println("task2 = " + task2);

        // 3. Список продуктов из категории "Игрушки" и примените скидку 10% и получите сумму всех продуктов.
        Predicate<Product> onlyToys = product -> product.category().equals("Игрушки");
        List<Product> task3 = productList.stream()
                .filter(onlyToys)
                .map(product -> {
                    BigDecimal discount = BigDecimal.valueOf(0.9);
                    BigDecimal newPrice = product.price().multiply(discount);
                    return new Product(product.id(), product.name(), product.category(), newPrice);
                })
                .toList();
        System.out.println("task3 = " + task3);

        // 4. Список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        Predicate<Customer> customerOnlyLevel2 = customer -> customer.level().equals(2L);
        LocalDate after = LocalDate.of(2021, 2, 1);
        LocalDate before = LocalDate.of(2021, 4, 1);
        Predicate<Order> orderBetweenFebApr = order -> order
                .orderDate().isAfter(after) && order
                .orderDate().isBefore(before);
        List<Product> task4 = customerList.stream()
                .filter(customerOnlyLevel2)
                .map(Customer::orders)
                .flatMap(Collection::stream)
                .filter(orderBetweenFebApr)
                .map(Order::products)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
        System.out.println("task4 = " + task4);

        // 5. топ 2 самые дешевые продукты из категории "Книги и канцелярия".
        Comparator<Product> productSortByPrice = Comparator.comparing(Product::price);
        List<Product> task5 = productList.stream()
                .sorted(productSortByPrice)
                .limit(2)
                .toList();
        System.out.println("task5 = " + task5);

        // 6. 3 самых последних сделанных заказа.
        Comparator<Order> orderSortByOrderDate = Comparator.comparing(Order::orderDate);
        List<Order> task6 = customerList.stream()
                .map(Customer::orders)
                .flatMap(Collection::stream)
                .sorted(orderSortByOrderDate)
                .limit(3)
                .toList();
        System.out.println("task6 = " + task6);

        // 7. список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните список их продуктов.
        LocalDate orderDate0315 = LocalDate.of(2021, 3, 15);
        Predicate<Order> orderInDate0315 = order -> order.orderDate().isEqual(orderDate0315);
        long counted = customerList.stream()
                .map(Customer::orders)
                .flatMap(Collection::stream)
                .filter(orderInDate0315)
                .peek(order -> {
                    System.out.println("id order: " + order.id());
                    order.products().forEach(System.out::println);
                }).count();
        System.out.println("task7. Counted = " + counted);

        // 8. Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        Predicate<Order> ordersInFeb = order -> order.orderDate().getMonth() == Month.FEBRUARY;
        BigDecimal task8 = customerList.stream()
                .map(Customer::orders)
                .flatMap(Collection::stream)
                .filter(ordersInFeb)
                .map(Order::products)
                .flatMap(Collection::stream)
                .map(Product::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("task8 = " + task8);

        // 9. Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        LocalDate orderDate0314 = LocalDate.of(2021, 3, 14);
        Predicate<Order> orderInDate0314 = order -> order.orderDate().isEqual(orderDate0314);
        List<Order> orders0314 = customerList.stream()
                .map(Customer::orders)
                .flatMap(Collection::stream)
                .filter(orderInDate0314)
                .toList();
        BigDecimal average;
        if (orders0314.isEmpty()) {
            average = BigDecimal.ZERO;
        } else {
            BigDecimal sumOrders = orders0314.stream()
                    .map(order -> order.products().stream()
                            .map(Product::price)
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            average = sumOrders.divide(BigDecimal.valueOf(orders0314.size()), RoundingMode.HALF_UP);
        }
        System.out.println("task9.average = " + average);

        // 10. Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех продуктов категории "Книги и канцелярия".
//        customerList.stream()


        // 11. Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе.
        Map<Long, Integer> task11 = customerList.stream()
                .map(Customer::orders)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        Order::id,
                        order -> order.products().size()));
        System.out.println("task11 = " + task11);

        // 12. Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов.
        Map<Customer, List<Order>> customerOrders = new HashMap<>();
        customerList.forEach(customer -> customerOrders.put(customer, customer.orders().stream().toList()));
        System.out.println("task12.v1 = " + customerOrders);

        Map<Customer, List<Order>> task12 = customerList.stream().collect(Collectors.toMap(
                customer -> customer,
                customer -> customer.orders().stream().toList()));
        System.out.println("task12.v2 = " + task12);

        // 13. Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        Map<Long, Double> task13 = customerList.stream()
                .flatMap(customer -> customer.orders().stream())
                .collect(Collectors.toMap(
                        Order::id,
                        order -> order.products().stream()
                                .map(Product::price)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .doubleValue()));
        System.out.println("task13 = " + task13);

        // 14. Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории.
        Map<String, List<String>> task14 = productList.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.mapping(Product::name, Collectors.toList())));
        System.out.println("task14 = " + task14);

        // 15. Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        Map<String, Product> map = new HashMap<>();
        for (Product product : productList) {
            String category = product.category();
            if (map.containsKey(category)) {
                BigDecimal maxPriceProduct = map.get(category).price();
                if (maxPriceProduct.compareTo(product.price()) < 0) {
                    map.put(category, product);
                }
            } else {
                map.put(category, product);
            }
        }
        System.out.println("task15 = " + map);

    }

    private static List<Product> generateProducts() {
        String[] categories = {
                "Бытовая техника",
                "Книги и канцелярия",
                "Одежда и аксессуары",
                "Дом и быт",
                "Спорт и отдых",
                "Игрушки",
                "Продукты питания",
                "Косметика и гигиена"
        };

        String[] names = {
                // Бытовая техника (8 товаров)
                "Пылесос", "Микроволновая печь", "Утюг", "Фен", "Тостер", "Кофемашина", "Миксер", "Блендер",
                // Книги и канцелярия (8)
                "Тетрадь", "Ручка шариковая", "Альбом для рисования", "Учебник по математике", "Детектив", "Роман", "Календарь настольный", "Скрепки",
                // Одежда и аксессуары (8)
                "Носки мужские", "Шарф шерстяной", "Перчатки кожаные", "Кепка бейсбольная", "Ремень кожаный", "Сумка женская", "Брюки классические", "Футболка с принтом",
                // Дом и быт (8)
                "Тарелка керамическая", "Кастрюля", "Полотенце махровое", "Вешалка пластиковая", "Корзина для белья", "Свечи ароматические", "Зеркало настольное", "Подставки под горячее",
                // Спорт и отдых (8)
                "Мяч футбольный", "Скакалка", "Коврик для йоги", "Гантели", "Ракетка для бадминтона", "Рюкзак туристический", "Спальный мешок", "Термос",
                // Игрушки (8)
                "Конструктор LEGO", "Кукла Barbie", "Машинка на радиоуправлении", "Пазл 1000 элементов", "Мягкие игрушки", "Набор для рисования", "Йо‑йо", "Железная дорога",
                // Продукты питания (8)
                "Молоко пастеризованное", "Хлеб белый", "Яйца куриные", "Сыр твёрдый", "Макароны", "Рис", "Сахар", "Масло подсолнечное",
                // Косметика и гигиена (8)
                "Зубная паста", "Дезодорант", "Крем для рук", "Шампунь", "Гель для душа", "Помада", "Тушь для ресниц", "Лосьон после бритья"
        };

        List<Product> products = new ArrayList<>();
        int itemsPerCategory = 8; // В каждой категории по 8 товаров
        for (int i = 0; i < categories.length; i++) {
            for (int j = 0; j < itemsPerCategory; j++) {
                int id = i + j;
                BigDecimal price = BigDecimal.valueOf(Math.round(10 + Math.random() * 90) * 100 / 100.0);
                products.add(new Product((long) id, names[id], categories[i], price));
            }
        }

        return products;
    }

    private static List<Customer> generateCustomers(List<Product> products) {
        List<Customer> customers = new ArrayList<>();
        String[] names = {"Rob", "Don", "Tom", "Lol", "Kek"};

        for (int i = 1; i <= 5; i++) {
            Long level = 1L + RANDOM.nextInt(5);
            Set<Order> orders = generateOrders(i, products);
            Customer customer = new Customer((long) i, names[i - 1], level, orders);
            customers.add(customer);
        }

        return customers;
    }

    private static Set<Order> generateOrders(int i, List<Product> products) {
        Set<Order> orders = new HashSet<>();
        LocalDate templateDate = LocalDate.of(2021, 3, 14);
        // У каждого клиента минимум 5 заказов
        for (int j = 1; j <= 5 + RANDOM.nextInt(3); j++) {
            Long orderId = i * 100L + j;
            LocalDate orderDate = templateDate.minusDays(RANDOM.nextInt(30));
            LocalDate deliveryDate = orderDate.plusDays(RANDOM.nextInt(10));
            String status = RANDOM.nextBoolean() ? "Delivered" : "Processing";

            Set<Product> productsInOrder = new HashSet<>();
            // В заказе от 1 до 6 продуктов
            int productCount = 1 + RANDOM.nextInt(6);
            for (int k = 0; k < productCount; k++) {
                Product randomProduct = products.get(RANDOM.nextInt(products.size()));
                productsInOrder.add(randomProduct);
            }

            Order order = new Order(orderId, orderDate, deliveryDate, status, productsInOrder);
            orders.add(order);
        }
        return orders;
    }
}

