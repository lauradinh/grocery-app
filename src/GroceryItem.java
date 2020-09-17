public class GroceryItem {
	private int id;
	private String name;
	private double price;
	private int quantity;

	public GroceryItem(int id, String name, double price, int initialQuantity) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = initialQuantity;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double newPrice) {
		this.price = newPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}

	@Override
	public String toString() {
		return String.format("%05d $%-8.2f%-5d%s", id, price, quantity, name);
	}
}
