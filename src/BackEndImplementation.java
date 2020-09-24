import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.IOException;

public class BackEndImplementation implements BackEnd {
  private boolean loggedIn = false;
  private HashTable<Integer, GroceryItem> groceryInfo;
  private ArrayList<CartItem> cart;
  private MapADT<String, String> accounts; // used to store worker usernames and passwords

  public BackEndImplementation() throws IOException {
    groceryInfo = DataAccess.getItems();
    cart = new ArrayList<CartItem>();
  }

  public boolean login(String username, String password) {
    try {
      String truePassword = accounts.get(username);
      if (password.equals(truePassword)) // worker entered the correct password
        loggedIn = true;
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public void quit() {
    loggedIn = false;
  }

  private boolean checkParameter(int id, String name, double price, int quantity) {
    if (quantity < 0 || price < 0)
      return false;
    return true;
  }

  public int addItem(int id, String name, double price, int quantity) {
    if (!loggedIn)
      return -1; // worker did not login
    if (!checkParameter(id, name, price, quantity))
      return -2; // invalid input data
    GroceryItem item = new GroceryItem(id, name, price, quantity);
    if (!groceryInfo.put(id, item)) // item wasn't added
      return -2;
    return 0; // item was successfully added
  }

  public int editItem(int id, String newName, double newPrice, int newQuantity) {
    if (!loggedIn)
      return -1; // worker didn't login
    if (!checkParameter(id, newName, newPrice, newQuantity))
      return -2; // invalid input data
    try {
      GroceryItem item = groceryInfo.get(id);
      item.setName(newName);
      item.setPrice(newPrice);
      item.setQuantity(newQuantity);
    } catch (NoSuchElementException e) {
      return -2; // item wasn't edited correctly
    }
    return 0; // item was edited correctly
  }

  public int removeItem(int id) {
    if (!loggedIn) {
      return -1; // worker didn't log in
    }
    if (groceryInfo.remove(id) == null) {
      return -2; // item wasn't in the cart
    }
    return 0; // item was removed from the cart
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
        CartItem cartItem = cart.get(id);
        if (cartItem.getQuantity() >= item.getQuantity()) {
          return -2; // invalid quantity requested
        }
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        return 0;
      } catch (NoSuchElementException e) {
        if (item.getQuantity() <= 0) {
          return -2; // item unavailable
        }
        CartItem cartItem = new CartItem(item, 1);
        cart.add(cartItem);
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
      if (cart.remove(id) != null) // remove method returned the value, item was removed
        return true;
      else
        return false; // item was not removed
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public float subtotal() {
    float subtotal = 0;
    for (int i = 0; i < cart.size(); i++) {
      try {
        CartItem item = cart.get(i);
        subtotal += item.getItem().getPrice() * item.getQuantity();
      } catch (NoSuchElementException e) {
        return -1; // error in fetching data
      }
    }
    return subtotal;
  }

  public boolean checkout() {
    for (int i = 0; i < cart.size(); i++) {
      try {
        CartItem cartItem = cart.get(i);
        GroceryItem item = groceryInfo.get(cartItem.getItem().getId());
        item.setQuantity(item.getQuantity() - cartItem.getQuantity());
      } catch (NoSuchElementException e) {
        return false;
      }
    }
    cart.clear();
    return true;
  }

  public ArrayList<CartItem> getCart() {
    return cart;
  }

}