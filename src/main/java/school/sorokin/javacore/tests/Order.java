package school.sorokin.javacore.tests;

public record Order(int id, String productName, int quantity, double unitPrice) {

    public double getTotalPrice() {
        if (unitPrice < 0 || quantity < 0) {
            throw new IllegalArgumentException();
        }
        return unitPrice * quantity;
    }
}
