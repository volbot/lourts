package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TexLoader {
    public ArrayList<Texture> tiles = new ArrayList<>();
    public ArrayList<Texture> towns = new ArrayList<>();
    public ArrayList<Texture> heroes = new ArrayList<>();
    public TexLoader(){
        loadTextures();
    }

    public void dispose(){
        for(Texture tex : tiles){
            tiles.remove(tex);
            tex.dispose();
        }
        for(Texture tex : towns){
            towns.remove(tex);
            tex.dispose();
        }
        for(Texture tex : heroes){
            heroes.remove(tex);
            tex.dispose();
        }
    }

    public void loadTextures(){
        File[] townsFiles = new File("towns/").listFiles();
        if(townsFiles!=null&&townsFiles.length>0) {
            for (File file : townsFiles) {
                towns.add(new Texture("towns/"+file.getName()));
            }
        }
        File[] tilesFiles = new File("tiles/").listFiles();
        if(tilesFiles!=null&&tilesFiles.length>0) {
            for (File file : tilesFiles) {
                tiles.add(new Texture("tiles/"+file.getName()));
            }
        }
        File[] heroesFiles = new File("heroes/").listFiles();
        if(heroesFiles!=null&&heroesFiles.length>0) {
            for (File file : heroesFiles) {
                heroes.add(new Texture("heroes/"+file.getName()));
            }
        }
    }
}
