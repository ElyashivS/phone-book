import java.util.TreeMap;

public class PhoneBook <K extends Comparable<K>, V>{
    private TreeMap<K, V> treeMap;

    public PhoneBook() {
        treeMap = new TreeMap<>();
    }

    public TreeMap<K, V> getTreeMap() {
        return treeMap;
    }
}
