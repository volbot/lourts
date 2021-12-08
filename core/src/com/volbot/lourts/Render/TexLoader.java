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
            tex.dispose();
            tiles.remove(tex);
        }
        for(Texture tex : towns){
            tex.dispose();
            towns.remove(tex);
        }
        for(Texture tex : heroes){
            tex.dispose();
            heroes.remove(tex);
        }
    }

    public void loadTextures(){
        File townsFolder = new File("towns/");
        File[] townsFiles = new File("towns/").listFiles();
        if(townsFiles!=null||townsFiles.length>0) {
            for (File file : townsFolder.listFiles()) {
                towns.add(new Texture("towns/"+file.getName()));
            }
        }
        File tilesFolder = new File("tiles/");
        File[] tilesFiles = new File("tiles/").listFiles();
        if(tilesFiles!=null||tilesFiles.length>0) {
            for (File file : tilesFolder.listFiles()) {
                tiles.add(new Texture("tiles/"+file.getName()));
            }
        }
        File heroesFolder = new File("heroes/");
        File[] heroesFiles = new File("heroes/").listFiles();
        if(heroesFiles!=null||heroesFiles.length>0) {
            for (File file : heroesFolder.listFiles()) {
                heroes.add(new Texture("heroes/"+file.getName()));
            }
        }
    }
}
