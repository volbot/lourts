package com.volbot.lourts.Map;

import java.util.ArrayList;

public class BattleMap extends GameMap{
    public BattleMap() {
        super("picklechin");
        this.chunks = new QuadNode(6, 0, 0, new Tile("grass"),"picklechin");
    }
}
