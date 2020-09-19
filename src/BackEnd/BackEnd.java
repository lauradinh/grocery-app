public interface BackEnd {
    /*
    Allow workers to login
    Return:
    If the worker logins successfully, return true. Otherwise return false
    */
    public boolean login(String username, String password);

    /*quit from the login state*/
    public void quit();

    /*
    Allow workers to add new items
    Return:
    If adding the item successfully, return 0
    If the worker does not login, return -1
    If the name, price, or quantity is not valid, return -2
     */
    public int addItem(int id, String name, double price, int quantity);

    /*
    Allow workers to edit any item in the catalogue
    Return:
    If editing the item successfully, return 0
    If the worker does not login, return -1
    If the id, name, price, or quantity is not valid, return -2
    */
    public int editItem(int id, String newName, double newPrice, int newQuantity);

    /*
    Allow workers to delete any item in the catalogue
    Return:
    If deleting the item successfully, return 0
    If the worker does not login or the program, return -1
    If the id is not valid or does not exist, return -2
    */
    public int removeItem(int id);

    /*
    save the change of the item catalogue
    Return:
    If save changes successfully, return true. Otherwise return false.
    */
    public boolean saveChange();

    /*
    Allow the user to add items to the cart without login
    Return:
    If adding successfully, return 0
    If the id is not valid, return -1
    If the item quantity is not greater than 0, return -2
    */
    public int addToCart(int id);

    /*
    Allow the user to delete items in the cart without login
    Return:
    If deleting successfully, return true, else if the id is not valid or not in the cart, return false
    */
    public boolean removeFromCart(int id);

    /*
    return the subtotal of the cart
    it should be positive, but it will be -1 if there is unexpected error
    */
    public int subtotal();

    /*
    Clear the cart and calculate new quantities of items in the cart
    Return:
    If success return true, otherwise return false
    */
    public boolean checkout();
}
