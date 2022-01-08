package com.volbot.lourts.Map;

public class Tile {
    public String texID;
    public boolean walkable;
    Tile(String material){
        if(material.equals("grass")){
            texID="grass";
            walkable=true;
        }
        if(material.equals("water")){
            texID="water";
            walkable=false;
        }
        if(material.equals("block")){
            texID="nothing";
            walkable=false;
        }
    }
}
