import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class GroceryApp {
	public static void main(String[] args) {
		// TODO: refactor this to implement our hash table
		HashMap<Integer, GroceryItem> groceryItems;

		try {
			groceryItems = DataAccess.getItems();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found");
			return;
		} catch (IOException e) {
			System.out.println("ERROR: Something went wrong in the IO department 🤷‍♂️");
			return;
		}

		/* Just testing the methods */
		groceryItems.keySet().forEach((key) -> System.out.println(groceryItems.get(key)));

		try {
			DataAccess.updateItems(groceryItems);
		} catch (IOException e) {
			System.out.println("ERROR: Could not write to file");
		}
		/* ======================== */
	}
}
