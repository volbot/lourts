package com.volbot.lourts.Map;

import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Main;

public class Lake extends MapFeature{
    public QuadNode map;
    public Lake(Vector3 position, int size) {
        this.position=position;
        this.map = new QuadNode(size, (int)position.x/20, (int)position.y/20, new Tile("water"));
    }
}
