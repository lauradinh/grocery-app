
public interface BackEnd {

  /**
   * 
   * Method to allow workers to login
   * 
   * @param username the worker's username to log in
   * @param password the worker's password to log in
   * @return true if the worker logs in successfully, false otherwise
   */
  public boolean login(String username, String password);

  /**
   * Method to log out of the worker's account
   */
  public void quit();

  /**
   * Method to allow workers to add new items
   * 
   * @param id       id number of item
   * @param name     name of the item
   * @param price    item's price
   * @param quantity item's quantity
   * @return 0 if the item was added successfully, -1 if the worker hasn't logged in, -2 for any
   *         invalid input data
   */
  public int addItem(int id, String name, double price, int quantity);

  /**
   * Allow workers to edit any item in the catalogue
   * 
   * @param id          id number of the item
   * @param newName     name that the item should be given
   * @param newPrice    price that the item should be given
   * @param newQuantity new quantity of the item
   * @return 0 if the item was successfully edited, -1 if the worker does not login, and -2 for any
   *         invalid input data
   */
  public int editItem(int id, String newName, double newPrice, int newQuantity);

  /**
   * Method to allow workers to delete any items in the catalogue
   * 
   * @param id id number of the item
   * @return 0 if the item is deleted successfully, -1 if the worker hasn't logged in, -2 if the id
   *         is invalid
   */
  public int removeItem(int id);

  /**
   * Method to save the changes in the item catalogue
   * 
   * @return true if the changed were saved successfully, false otherwise
   */
  public boolean saveChange();

  /**
   * Allow the user to add items to the cart without login
   * 
   * @param id id number of the item
   * @return 0 if the item was added successfully, -1 if the id number is invalid, -2 if the item
   *         quantity is negative
   */
  public int addToCart(int id);

  /**
   * Allow the user to delete items in the cart without login
   * 
   * @param id id number of the item
   * @return true if the item was removed successfully, false otherwise
   */
  public boolean removeFromCart(int id);

  /**
   * Method that returns the subtotal of prices of items in the cart
   * 
   * @return subtotal amount, -1 if there is an unexpected error
   */
  public float subtotal();

  /**
   * Method that clears the cart and calculates the remaining quantities of grocery items after sale
   * 
   * @return true if successful, false otherwise
   */
  public boolean checkout();
}
