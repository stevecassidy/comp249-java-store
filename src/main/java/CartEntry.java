public class CartEntry {
    private int productId;
    private int quantity;
    private String name;
    private double cost;

    public CartEntry(int productId, int quantity, String name, double cost) {
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.cost = cost;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
