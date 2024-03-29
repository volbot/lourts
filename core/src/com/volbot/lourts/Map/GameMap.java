package com.volbot.lourts.Map;

import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Main;
import com.volbot.lourts.Render.TexUnit;

import java.util.ArrayList;

public class GameMap {

    public String name;
    public QuadNode chunks;
    public String SEED;

    public GameMap(String name, String seed) {
        this.name = name;
        SEED=seed;
        chunks = new QuadNode(9, 0, 0, new Tile("grass"), seed);

        chunks.insert(6, 6, new Tile("block"));
        chunks.insert(2, 6, new Tile("block"));
        chunks.insert(1, 3, new Tile("block"));
        chunks.insert(7, 3, new Tile("block"));
        chunks.insert(6, 2, new Tile("block"));
        chunks.insert(5, 2, new Tile("block"));
        chunks.insert(4, 2, new Tile("block"));
        chunks.insert(3, 2, new Tile("block"));
        chunks.insert(2, 2, new Tile("sand"));

        int len = 20 * (int) Math.pow(2, chunks.depth);
        Perlin p = new Perlin(SEED);
        Vector3 seedVec;
        float temp;
        for (int x = 0; x < len; x += 40) {
            for (int y = 0; y < len; y += 40) {
                seedVec = new Vector3(x, y, 0);
                temp = p.noise(seedVec.cpy().scl(30));
                if (temp < -80) {
                    dropWater(x, y, 15);
                }
            }
        }

        for (int x = 0; x < len; x += 40) {
            for (int y = 0; y < len; y += 40) {
                temp = Main.random.nextInt(1000);
                if (temp <= 2 && chunks.getTile(x/20,y/20).walkable) {
                    temp = Main.random.nextInt(Main.texLoader.texUnits.values().size());
                    int i = 0;
                    String texUnit = null;
                    for(TexUnit u : Main.texLoader.texUnits.values()){
                        if(i==temp){
                            texUnit = u.name;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if(texUnit==null) texUnit="base";
                    Main.locations.add(new Location("Testland", x, y, texUnit, 200));
                }
            }
        }
    }

    public int size() {
        return 20 * (int) Math.pow(2, chunks.depth);
    }

    public void dropWater(int xIn, int yIn, int wiggle) {
        int x = xIn / 20;
        int y = yIn / 20;
        QuadNode chunk = chunks.get(x, y);
        if (chunk != null) {
            chunk.tile = new Tile("water");
            QuadNode left = chunks.get(x - 1, y);
            if (left != null && !(left.tile.tileType.equals("water"))) {
                if (left.height <= chunk.height + wiggle) {
                    dropWater(xIn - 20, yIn, wiggle);
                } else {
                    left.tile = new Tile("sand");
                }
            }
            QuadNode right = chunks.get(x + 1, y);
            if (right != null && !(right.tile.tileType.equals("water"))) {
                if (right.height <= chunk.height + wiggle) {
                    dropWater(xIn + 20, yIn, wiggle);
                } else {
                    right.tile = new Tile("sand");
                }
            }
            QuadNode top = chunks.get(x, y + 1);
            if (top != null && !(top.tile.tileType.equals("water"))) {
                if (top.height <= chunk.height + wiggle) {
                    dropWater(xIn, yIn + 20, wiggle);
                } else {
                    top.tile = new Tile("sand");
                }
            }
            QuadNode bot = chunks.get(x, y - 1);
            if (bot != null && !(bot.tile.tileType.equals("water"))) {
                if (bot.height <= chunk.height + wiggle) {
                    dropWater(xIn, yIn - 20, wiggle);
                } else {
                    bot.tile = new Tile("sand");
                }
            }
        }
    }

    public ArrayList<Vector3> neighbors(Vector3 init) {
        ArrayList<Vector3> ret = new ArrayList<>();
        ret.add(init);
        ret.add(init.cpy().add(20, 0, 0));
        ret.add(init.cpy().add(-20, 0, 0));
        ret.add(init.cpy().add(0, 20, 0));
        ret.add(init.cpy().add(0, -20, 0));
        return ret;
    }

    public Vector3 nextPoint(Vector3 curr, Vector3 dest) {
        return null;
    }
}
