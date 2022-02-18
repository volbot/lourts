package com.volbot.lourts.Map;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GameMap {

    public QuadNode chunks;

    public GameMap() {
        chunks = new QuadNode(9, 0, 0, new Tile("sand"));

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
        Perlin p = new Perlin();
        Vector3 seed;
        float temp;
        for (int x = 0; x < len; x += 40) {
            for (int y = 0; y < len; y += 40) {
                seed = new Vector3(x, y, 0);
                temp = p.noise(seed.cpy().scl(30));
                if (temp < -80) {
                    dropWater(x, y, 15);
                }
            }
        }
    }

    public int size() {
        return 20 * (int) Math.pow(2, chunks.depth);
    }

    public void dropWater(int x, int y) {
        if (chunks.get(x/20, y/20) != null)
            dropWater(x, y, 0);
    }

    public void dropWater(int xIn, int yIn, int wiggle) {
        int x = xIn/20;
        int y = yIn/20;
        QuadNode chunk = chunks.get(x, y);
        if (chunk != null) {
            chunk.tile = new Tile("water");
            QuadNode left = chunks.get(x - 1, y);
            if (left != null && left.height <= chunk.height+wiggle && !left.tile.tileType.equals("water")) {
                dropWater(xIn-20, yIn, wiggle);
            }
            QuadNode right = chunks.get(x + 1, y);
            if (right != null && right.height <= chunk.height+wiggle && !right.tile.tileType.equals("water")) {
                dropWater(xIn+20, yIn, wiggle);
            }
            QuadNode top = chunks.get(x, y + 1);
            if (top != null && top.height <= chunk.height+wiggle && !top.tile.tileType.equals("water")) {
                dropWater(xIn, 20 * yIn+20, wiggle);
            }
            QuadNode bot = chunks.get(x, y - 1);
            if (bot != null && bot.height <= chunk.height+wiggle && !bot.tile.tileType.equals("water")) {
                dropWater(xIn, yIn-20, wiggle);
            }
        }
    }

    public ArrayList<Vector3> neighbors(Vector3 init) {
        ArrayList<Vector3> ret = new ArrayList<>();
        ret.add(init);
        ret.add(init.cpy().add(20,0,0));
        ret.add(init.cpy().add(-20,0,0));
        ret.add(init.cpy().add(0,20,0));
        ret.add(init.cpy().add(0,-20,0));
        return ret;
    }

    public Vector3 nextPoint(Vector3 curr, Vector3 dest) {
        return null;
    }
}
