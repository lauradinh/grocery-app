import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class SimpleHashTable<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private HashMap<KeyType, ValueType> map;

  // constructors
  public SimpleHashTable() {
    map = new HashMap<KeyType, ValueType>();
  }

  public SimpleHashTable(HashMap map) {
    this.map = map;
  }

  /**
   * Method to put a new key-value pair into the hash table. Return false When the put method is
   * passed a key that is already in your hash table without making any changes to the hash table.
   * 
   * @param key   key of the key-value pair to be stored
   * @param value of the key-value pair to be stored
   * @return true if the value is successfully stored, false if not
   */
  @Override
  public boolean put(KeyType key, ValueType value) {
    ValueType original = map.put(key, value); // checks value currently associated with key
    if (original == null) // there was no value previously associated with this key
      return true; // value added
    else {
      map.put(key, original); // original value is reassociated with key
      return false; // value was returned and not stored
    }
  }

  /**
   * Method to access the value of a key-value pair using the key.
   * 
   * @param key key of the key-value pair to be found
   * @return value of the key-value pair
   * @throws NoSuchElementException if there is no such key in the hash map
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    ValueType value = map.get(key);
    if (value == null)
      throw new NoSuchElementException();
    return value;
  }

  /**
   * Method that returns the number of key-values pairs currently in the array.
   * 
   * @return number of key-value pairs in the array
   */
  @Override
  public int size() {
    return map.size();
  }

  /**
   * Method to check whether or not a specific key is contained in the array
   * 
   * @param key key to be checked
   * @return true if the key is in the array, false if otherwise
   */
  @Override
  public boolean containsKey(KeyType key) {
    return map.containsKey(key);
  }

  /**
   * Removes the mapping for the specified key from this map if present.
   * 
   * @param key key to be removed
   * @return value associated with the key being removed
   * 
   */
  @Override
  public ValueType remove(KeyType key) {
    return map.remove(key);
  }

  /**
   * Removes all of the mappings from this map.
   */
  @Override
  public void clear() {
    map.clear();
  }

  public HashMap getMap() {
    return map;
  }

  public KeyType[] keys() {
    return (KeyType[]) map.keySet().toArray();
  }
}
