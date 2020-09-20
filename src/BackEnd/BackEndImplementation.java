import java.util.NoSuchElementException;
import java.io.IOException;

public class BackEndImplementation implements BackEnd {

    private boolean login=false;
    private MapADT<Integer, GroceryItem> groceryInfo;
    private SimpleHashTable<Integer, CartItem> cart;
    private MapADT<String, String> accounts; // used to store worker usernames and passwords

    public BackEndImplementation() throws IOException {
        groceryInfo = new SimpleHashTable<Integer, GroceryItem>(DataAccess.getItems());
        cart = new SimpleHashTable<Integer, CartItem>();
    }

    public boolean login(String username, String password) {
        try {
            String truePassword = accounts.get(username);
            if (password.equals(truePassword))
                return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void quit() {
        login = false;
    }

    private boolean checkParameter(int id, String name, double price, int quantity) {
        if (quantity < 0 || price < 0)
            return false;
        return true;
    }

    public int addItem(int id, String name, double price, int quantity) {
        if (!login)
            return -1;
        if (!checkParameter(id, name, price, quantity))
            return -2;
        GroceryItem item = new GroceryItem(id, name, price, quantity);
        if (!groceryInfo.put(id, item))
            return -2;
        return 0;
    }

    public int editItem(int id, String newName, double newPrice, int newQuantity) {
        if (!login)
            return -1;
        if (!checkParameter(id, newName, newPrice, newQuantity))
            return -2;
        try {
            GroceryItem item = groceryInfo.get(id);
            item.setName(newName);
            item.setPrice(newPrice);
            item.setQuantity(newQuantity);
        } catch (NoSuchElementException e) {
            return -2;
        }
        return 0;
    }

    public int removeItem(int id) {
        if (!login)
            return -1;
        if (groceryInfo.remove(id) == null)
            return -2;
        return 0;
    }

    public boolean saveChange() {
        try {
            DataAccess.updateItems(((SimpleHashTable) groceryInfo).getMap());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public int addToCart(int id) {
        try {
            GroceryItem item = groceryInfo.get(id);
            try {
                CartItem cartItem = cart.get(id);
                if (cartItem.getQuantity() >= item.getQuantity())
                    return -2;
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return 0;
            } catch (NoSuchElementException e) {
                if (item.getQuantity() <= 0)
                    return -2;
                CartItem cartItem = new CartItem(item, 1);
                cart.put(id, cartItem);
                return 0;
            }
        } catch (NoSuchElementException e) {
            return -1;
        }
    }

    public boolean removeFromCart(int id) {
        try {
            CartItem item = cart.get(id);
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                return true;
            }
            if (cart.remove(id) != null)
                return true;
            else
                return false;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public float subtotal() {
        Object[] keys = cart.keys();
        float subtotal = 0;
        for (int i = 0; i < keys.length; i++) {
            try {
                CartItem item = cart.get((Integer) keys[i]);
                subtotal += item.getItem().getPrice() * item.getQuantity();
            } catch (NoSuchElementException e) {
                return -1;
            }
        }
        return subtotal;
    }

    public boolean checkout() {
        Object[] keys = cart.keys();
        for (int i = 0; i < keys.length; i++) {
            try {
                CartItem cartItem = cart.get((Integer) keys[i]);
                GroceryItem item = groceryInfo.get((Integer) keys[i]);
                item.setQuantity(item.getQuantity() - cartItem.getQuantity());
            } catch (NoSuchElementException e) {
                return false;
            }
        }
        cart.clear();
        return true;
    }

    public SimpleHashTable getCart() {
        return cart;
    }
}
