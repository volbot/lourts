package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.Texture;
import com.volbot.lourts.Main;

import java.util.ArrayList;

public class TexUnit {
    public String name;

    public ArrayList<Texture> towns = new ArrayList<>();
    public ArrayList<Texture> heroes = new ArrayList<>();
    public ArrayList<Texture> combatants = new ArrayList<>();
    public ArrayList<String> names1 = new ArrayList<>();
    public ArrayList<String> names2 = new ArrayList<>();
    public ArrayList<String> names3 = new ArrayList<>();

    public TexUnit(String name) {
        this.name=name;
    }

    public String getName() {
        return names1.get(Main.random.nextInt(names1.size()))+" "+
                names2.get(Main.random.nextInt(names2.size()))+" "+
                names3.get(Main.random.nextInt(names3.size()));
    }
}
