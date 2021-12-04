package Data;

import org.lwjgl.system.NonnullDefault;

import java.util.HashMap;

public class Reputation {
    private final HashMap<String, Integer> repmap = new HashMap<>();
    public Reputation() { }
    public int get(String key){
        return repmap.get(key);
    }
    public void meet(String name){
        repmap.put(name,0);
    }
}
