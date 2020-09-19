import java.util.NoSuchElementException;
import java.io.IOException;

public class BackEndImplementation implements BackEnd {

    private boolean login=false;
    private MapADT<Integer, GroceryItem> groceryInfo;
    private MapADT<Integer, GroceryItem> cart;
    private MapADT<String, String> accounts; // used to store worker usernames and passwords

    public BackEndImplementation() {
        groceryInfo=DataAccess.getItems();
    }

    public boolean login(String username, String password) {
        try{
            String truePassword=accounts.get(username);
            if(password.equals(truePassword))
                return true;
            return false;
        }catch(Exception e) {
            return false;
        }
    };

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
        if (!checkParameter(id, name, priec, quantity))
            return -2;
        GroceryItem item = new GroceryItem(id, name, price, quantity);
        if (!groceryInfo.put(id, GroceryItem)) ;
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
            DataAccess.updateItems(groceryInfo);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public int addToCart(int id) {
        try {
            GroceryItem item = groceryInfo.get(id);
            try {
                GroceryItem cartItem = cart.get(id);
                if (cartItem.getQuantity() > item.getQuantity())
                    return -2;
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return 0;
            } catch (NoSuchElementException e) {
                if (item.getQuantity() <= 0)
                    return -2;
                GroceryItem cartItem = new GroceryItem(id, item.name, item.price, 1);
                cart.put(id, cartItem);
                return 0;
            }
        } catch (NoSuchElementException e) {
            return -1;
        }
    }

    public boolean removeFromCart(int id) {
        try {
            GroceryItem item = cart.get(id);
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                return true;
            }
            if (cart.remove(item) != null)
                return true;
            else
                return false;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public int subtotal() {
        Integer[] keys = cart.list(); // need to implement
        int subtotal = 0;
        for (int i = 0; i < keys.length; i++) {
            try {
                GroceryItem item = cart.get(keys[i]);
                subtotal += item.getPrice() * item.getQuantity();
            } catch (NoSuchElementException e) {
                return -1;
            }
        }
        return subtotal;
    }

    public boolean checkout() {
        Integer[] keys = cart.list(); // need to implement
        for(int i=0;i<keys.length;i++) {
            try {
                GroceryItem cartItem = cart.get(keys[i]);
                GroceryItem item=groceryInfo.get(keys[i]);
                item.setQuantity(item.getQuantity()-cartItem.getQuantity());
            } catch (NoSuchElementException e) {
                return false;
            }
        }
        cart.clear();
        return true;
    }
}