import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.LinkedList;

public class HashTable<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
    private HashSet<KeyType> keySet;
    private LinkedList<Tuple<KeyType, ValueType>>[] table;
    private int capacity;
    private int numElements;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.capacity = Math.max(capacity, 1);
        table = new LinkedList[this.capacity];
        keySet = new HashSet<KeyType>();
    }

    public HashTable() {
        this(10);
    }

    @Override
    @SuppressWarnings("unchecked")
    /**
     * Adds key and associated value to the hash table.
     * 
     * @param key   The key associated with the value being added.
     * @param value The objected being added.
     * @return {@code true} if the key does not already exist in the hash table;
     *         {@code false} if the key already exists in the hash table.
     */
    public boolean put(KeyType key, ValueType value) {
        // Don't add value to map if the key is null
        if (key == null) {
            return false;
        }

        // Calculate the new item's hash index
        int newItemHashIndex = getHashIndex(key);

        // Return unsuccessfully if there is already data associated with key
        if (table[newItemHashIndex] != null) {
            for (var item : table[newItemHashIndex]) {
                if (item.getKey().equals(key)) {
                    return false;
                }
            }
        }

        // If the table is at least 80% full, resize it
        if (isFull()) {
            capacity *= 2;

            LinkedList<Tuple<KeyType, ValueType>>[] newTable = new LinkedList[capacity];

            for (var list : table) {
                if (list == null) {
                    continue;
                }

                for (var item : list) {
                    if (item == null) {
                        continue;
                    }

                    int hashIndex = getHashIndex(item.getKey());

                    if (newTable[hashIndex] == null) {
                        newTable[hashIndex] = new LinkedList<Tuple<KeyType, ValueType>>();
                    }

                    newTable[hashIndex].add(new Tuple<KeyType, ValueType>(item.getKey(), item.getValue()));
                }
            }

            table = newTable;

            newItemHashIndex = getHashIndex(key);
        }

        // Instantiate a new LinkedList if there isn't one already in that hash index
        if (table[newItemHashIndex] == null) {
            table[newItemHashIndex] = new LinkedList<Tuple<KeyType, ValueType>>();
        }

        // Put the key-value pair in the linked list
        table[newItemHashIndex].add(new Tuple<KeyType, ValueType>(key, value));

        // Add key to key set
        keySet.add(key);

        numElements++;

        return true;
    }

    @Override
    /**
     * Finds the value associated with a given {@code key}
     * 
     * @param key The key associated with the value.
     * @return value associated with key, if the key is in the hash table;
     *         {@code NoSuchElementException} if it isn't.
     */
    public ValueType get(KeyType key) throws NoSuchElementException {
        // Don't look for a value if the key is null
        if (key == null) {
            return null;
        }

        int hashIndex = getHashIndex(key);

        // There can't be a value if there is no linked list
        if (table[hashIndex] == null) {
            throw new NoSuchElementException();
        }

        // Find and return the value of the item with the provided key
        for (var item : table[hashIndex]) {
            if (item.getKey().equals(key)) {
                return item.getValue();
            }
        }

        // If an item with the provided key was not found, it does not exist
        throw new NoSuchElementException();
    }

    @Override
    /**
     * Returns the current number of elements in the hash table.
     * 
     * @return {@code numElements}
     */
    public int size() {
        return numElements;
    }

    @Override
    /**
     * Determines whether a key is present in the hash table or not.
     * 
     * @return {@code true} if the key is present; {@code false} if not.
     */
    public boolean containsKey(KeyType key) {
        // Don't look for a value if the key is null
        if (key == null) {
            return false;
        }

        int hashIndex = getHashIndex(key);

        // There can't be a value if there is no linked list
        if (table[hashIndex] == null) {
            return false;
        }

        // Look for key in linked list
        for (var item : table[hashIndex]) {
            if (item.getKey().equals(key)) {
                return true;
            }
        }

        // If the key wasn't found, it doesn't exist
        return false;
    }

    @Override
    /**
     * Removes a key along with its associated value from the hash table.
     * 
     * @param key The key associated with the value being removed.
     * @return Value of associted key, if it exists; {@code null} if not.
     */
    public ValueType remove(KeyType key) {
        // Can't remove item if key is null
        if (key == null) {
            return null;
        }

        int hashIndex = getHashIndex(key);

        // There can't be a value if there is no linked list
        if (table[hashIndex] == null) {
            return null;
        }

        for (int i = 0; i < table[hashIndex].size(); i++) {
            var item = table[hashIndex].get(i);

            if (item.getKey().equals(key)) {
                // Remove this element from the linked list
                numElements--;

                // Remove key from key set
                keySet.remove(key);

                return table[hashIndex].remove(i).getValue();
            }
        }

        return null;
    }

    /**
     * Returns the set of keys associated with values in the hash table
     * 
     * @return Set of keys in hash table
     */
    public HashSet<KeyType> keySet() {
        return keySet;
    }

    @Override
    @SuppressWarnings("unchecked")
    /**
     * Deletes all items from the collection.
     */
    public void clear() {
        // "Clear" table by instantiating a new array
        table = new LinkedList[capacity];

        numElements = 0;
    }

    /**
     * Calculates and returns key's hash index
     * 
     * @param key A key associated with a value.
     * @return {@code key.hashCode() % capacity}
     */
    private int getHashIndex(KeyType key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Returns a boolean indicating whether the load factor is greater than or equal
     * to 80%.
     * 
     * @return load factor >= 80%
     */
    private boolean isFull() {
        return getLoadFactor() >= 0.8;
    }

    /**
     * Calculates and returns the load factor of the hash table.
     * 
     * @return load factor
     */
    private double getLoadFactor() {
        return (double) (numElements + 1) / capacity;
    }

    // Utility class to hold the keys and values in the same place
    private class Tuple<X, Y> {
        private X key;
        private Y value;

        public Tuple(X key, Y value) {
            this.key = key;
            this.value = value;
        }

        public X getKey() {
            return key;
        }

        public Y getValue() {
            return value;
        }
    }
}
