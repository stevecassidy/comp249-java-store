import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class StoreDAO {

    private HashMap<Integer,Product> productStore;
    private HashMap<String, ArrayList<CartEntry>> cartStore;

    public StoreDAO() {
        productStore = new HashMap<>();
        cartStore = new HashMap<>();
    }

    public ArrayList<Product> getProducts() {
        return new ArrayList<>(productStore.values());
    }

    public ArrayList<Product> getProductsInCategory(String category) {
        ArrayList<Product> result = new ArrayList<>();

        for(Product product : productStore.values()) {
            if (product.getCategory().equals(category)) {
                result.add(product);
            }
        }
        return result;
    }

    public Product getProduct(int id) {
        return productStore.get(id);
    }

    public boolean addProduct(Product p) {
        productStore.put(p.getId(), p);
        return true;
    }

    /* shopping cart */

    public ArrayList<CartEntry> getCart(String sessionid) {
        ArrayList<CartEntry> result = cartStore.get(sessionid);
        if (result != null) {
            return result;
        } else {
            return new ArrayList<CartEntry>();
        }
    }

    public void addToCart(String sessionid, int productId, int quantity) {

        Product product = getProduct(productId);

        ArrayList<CartEntry> cart = getCart(sessionid);

        boolean found = false;
        for (CartEntry entry : cart) {
            if (entry.getProductId() == productId) {
                /* increment quantity */
                entry.setQuantity(entry.getQuantity()+quantity);
                entry.setCost(entry.getQuantity()* product.getUnitCost());
                found = true;
            }
        }
        if (!found) {
            double cost = product.getUnitCost() * quantity;
            CartEntry c = new CartEntry(productId, quantity, product.getName(), cost);
            cart.add(c);
        }
        cartStore.put(sessionid, cart);
    }

}
