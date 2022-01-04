package com.volbot.lourts.Map;

public class Tile {
    public int texID;
    public boolean walkable;
    Tile(String material){
        if(material.equals("grass")){
            texID=0;
            walkable=true;
        }
        if(material.equals("water")){
            texID=2;
            walkable=false;
        }
        if(material.equals("block")){
            texID=1;
            walkable=false;
        }
    }
}
