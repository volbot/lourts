package com.volbot.lourts.Render;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TexLoader {
    public HashMap<String, Texture> tiles = new HashMap<>();

    public HashMap<String, TexUnit> texUnits;

    public TexLoader() {
        texUnits = new HashMap<>();
        loadTextures();
    }

    public void dispose() {
        for (String tileName : tiles.keySet()) {
            tiles.get(tileName).dispose();
            tiles.remove(tileName);
        }
        for (TexUnit unit : texUnits.values()) {
            for (Texture tex : unit.towns) {
                unit.towns.remove(tex);
                tex.dispose();
            }
            for (Texture tex : unit.heroes) {
                unit.heroes.remove(tex);
                tex.dispose();
            }
            for (Texture tex : unit.combatants) {
                unit.combatants.remove(tex);
                tex.dispose();
            }
        }
    }

    public void loadTextures() {
        File[] tilesFiles = new File("tiles/").listFiles();
        if (tilesFiles != null && tilesFiles.length > 0) {
            for (File file : tilesFiles) {
                tiles.put(file.getName().substring(0, file.getName().indexOf(".")), new Texture("tiles/" + file.getName()));
            }
        }

        File[] units = new File("texUnits/").listFiles();
        if (units != null && units.length > 0)
            for (File unitFolder : units) {
                String unitName = unitFolder.getName();
                if (!texUnits.containsKey(unitName))
                    texUnits.put(unitName, new TexUnit());
                File[] townsFiles = new File(unitFolder.getPath() + "/towns/").listFiles();
                if (townsFiles != null && townsFiles.length > 0)
                    for (File file : townsFiles)
                        texUnits.get(unitName).towns.add(new Texture(file.getPath()));
                File[] heroesFiles = new File(unitFolder.getPath() + "/heroes/").listFiles();
                if (heroesFiles != null && heroesFiles.length > 0)
                    for (File file : heroesFiles)
                        texUnits.get(unitName).heroes.add(new Texture(file.getPath()));
                File[] combatantsFiles = new File(unitFolder.getPath() + "/combatants/").listFiles();
                if (combatantsFiles != null && combatantsFiles.length > 0)
                    for (File file : combatantsFiles)
                        texUnits.get(unitName).combatants.add(new Texture(file.getPath()));
            }
    }
}
