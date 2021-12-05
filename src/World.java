import Data.Coordinates;
import Data.Location;

import java.util.HashMap;

public class World {
    private HashMap<Coordinates,Location> locations = new HashMap<>();
    String name;
    private int[][] tiles;

    public World(String name, int width, int height){
        this.name=name;
        tiles = new int[width][height];
    }
}
