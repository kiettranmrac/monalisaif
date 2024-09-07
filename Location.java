import java.util.ArrayList;
import java.util.HashMap;

public class Location {
    
    public String name;
    public String description;
    public boolean isVisited = false;

    public HashMap<String, Location> exits = new HashMap<String, Location>();
    
    public Location(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public ArrayList<Item> items = new ArrayList<Item>();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (name.equals("Inventory")) {
            sb.append("\nYour items: ");
            for (Item item : items) {
                sb.append(item + " ");
            }
            return sb.toString();
        }
        if (!isVisited) {
            sb.append("\n" + description);
            isVisited = true;
        }
        if (!exits.isEmpty()) {
            sb.append("\nPossible exits: ");
            for (String direction : exits.keySet()) {
                sb.append(direction + " ");
            }
        }
        if (!items.isEmpty()) {
            sb.append("\nYou see: ");
            for (Item item : items) {
                sb.append(item + " ");
            }
        }
        return sb.toString();
    }

}
