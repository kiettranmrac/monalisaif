import java.util.Set;
import java.util.TreeSet;

public class Item {
    
    public String name;
    public String description;
    public Set<Object> attributes = new TreeSet<Object>();

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String toString() {
        return name;
    }
}
