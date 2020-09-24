import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class DataAccess {
	public static final String PATH = "./data/grocery-items.csv";

	public static HashTable<Integer, GroceryItem> getItems() throws FileNotFoundException, IOException {
		var br = new BufferedReader(new InputStreamReader(DataAccess.class.getClassLoader().getResourceAsStream(PATH)));

		var map = new HashTable<Integer, GroceryItem>();

		for (String row = br.readLine(); row != null; row = br.readLine()) {
			String[] columns = row.split(",");

			int id = Integer.valueOf(columns[0]);
			String name = columns[1];
			double price = Double.valueOf(columns[2]);
			int quantity = Integer.valueOf(columns[3]);

			map.put(id, new GroceryItem(id, name, price, quantity));
		}

		br.close();

		return map;
	}

	public static void updateItems(HashTable<Integer, GroceryItem> items) throws IOException {
		BufferedWriter bw;

		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(DataAccess.class.getResource(PATH).toURI().getPath()))));
		} catch (URISyntaxException e) {
			throw new IOException();
		}

		for (var key : items.keySet()) {
			GroceryItem item = items.get(key);
			bw.write(String.format("%d,%s,%.2f,%d\n", item.getId(), item.getName(), item.getPrice(),
					item.getQuantity()));
		}

		bw.close();
	}
}
