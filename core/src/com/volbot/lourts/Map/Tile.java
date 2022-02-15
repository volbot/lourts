package com.volbot.lourts.Map;

public class Tile {
    public String tileType;
    public boolean walkable;
    Tile(String material){
        if(material.equals("grass")){
            tileType ="grasst";
            walkable=true;
        }
        if(material.equals("water")){
            tileType ="water";
            walkable=false;
        }
        if(material.equals("block")){
            tileType ="mountain";
            walkable=false;
        }
    }
}
