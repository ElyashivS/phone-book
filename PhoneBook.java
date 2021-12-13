import java.io.Serializable;
import java.util.TreeMap;

public class PhoneBook <K extends Comparable<K>, V> implements Serializable {
    private TreeMap<K, V> treeMap;

    public PhoneBook() {
        treeMap = new TreeMap<>();
    }

    public TreeMap<K, V> getTreeMap() {
        return treeMap;
    }
}
