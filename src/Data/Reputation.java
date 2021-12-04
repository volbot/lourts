package Data;

import java.util.HashMap;

public class Reputation {
    private final HashMap<String, Integer> repmap = new HashMap<>();

    public Reputation() {
    }

    public boolean isset(String key){
        return repmap.containsKey(key);
    }

    public int get(String key) {
        return repmap.get(key);
    }

    public void meet(String name) {
        repmap.put(name, 0);
    }
}
