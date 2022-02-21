package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class TexUnit {
    public String name;

    public ArrayList<Texture> towns = new ArrayList<>();
    public ArrayList<Texture> heroes = new ArrayList<>();
    public ArrayList<Texture> combatants = new ArrayList<>();

    public TexUnit(String name) {
        this.name=name;
    }
}
