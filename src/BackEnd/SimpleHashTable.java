import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class SimpleHashTable<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
    private HashMap<KeyType, ValueType> map;
    public SimpleHashTable() {
        map=new HashMap<KeyType, ValueType>();
    }
    public SimpleHashTable(HashMap map) {
        this.map=map;
    }
    @Override
    public boolean put(KeyType key, ValueType value) {
        ValueType original=map.put(key, value);
        if(original==null)
            return true;
        else {
            map.put(key, original);
            return false;
        }
    }

    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        ValueType value=map.get(key);
        if(value==null)
            throw new NoSuchElementException();
        return value;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean containsKey(KeyType key) {
        return map.containsKey(key);
    }

    @Override
    public ValueType remove(KeyType key) {
        return map.remove(key);
    }

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
