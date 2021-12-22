import java.io.Serializable;
import java.util.TreeMap;

/**
 * This class represent the phonebook class.
 * @param <K> the key, AKA name
 * @param <V> the value, AKA Phone Number
 */
public class PhoneBook <K extends Comparable<K>, V> implements Serializable {
    private TreeMap<K, V> treeMap;

    // Constructor
    public PhoneBook() {
        treeMap = new TreeMap<>();
    }

    // Getter
    public TreeMap<K, V> getTreeMap() {
        return treeMap;
    }
}
