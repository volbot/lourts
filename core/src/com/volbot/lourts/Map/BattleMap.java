package com.volbot.lourts.Map;

public class BattleMap extends GameMap{
    public BattleMap() {
        super("battle","picklechin");
        this.chunks = new QuadNode(6, 0, 0, new Tile("grass"),"picklechin");
    }
}
