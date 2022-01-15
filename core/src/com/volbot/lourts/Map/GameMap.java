package com.volbot.lourts.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Main;

public class GameMap {

    public QuadNode chunks;

    public GameMap() {
        chunks = new QuadNode(10, 0, 0, new Tile("grass"));

        chunks.insert(6, 6, new Tile("block"));
        chunks.insert(2, 6, new Tile("block"));
        chunks.insert(1, 3, new Tile("block"));
        chunks.insert(7, 3, new Tile("block"));
        chunks.insert(6, 2, new Tile("block"));
        chunks.insert(5, 2, new Tile("block"));
        chunks.insert(4, 2, new Tile("block"));
        chunks.insert(3, 2, new Tile("block"));
        chunks.insert(2, 2, new Tile("block"));

        Lake lakeDoramos = new Lake(new Vector3(40,600,0),4);
        Lake lakePickleChin = new Lake(new Vector3(400, 30, 0), 3);
        Lake lakePickleChin2 = new Lake(new Vector3(660, 60, 0), 4);

        chunks.insert(lakeDoramos.map);
        chunks.insert(lakePickleChin.map);
        chunks.insert(lakePickleChin2.map);
    }

    public int size() {
        return 20 * (int) Math.pow(2, chunks.depth);
    }
}
