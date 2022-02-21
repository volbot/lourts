package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class TexLoader {
    public HashMap<String, Texture> tiles = new HashMap<>();

    public HashMap<String, TexUnit> texUnits;

    public TexLoader() {
        texUnits = new HashMap<>();
        loadTiles();
    }

    public void dispose() {
        for (String tileName : tiles.keySet()) {
            tiles.get(tileName).dispose();
            tiles.remove(tileName);
        }
        for (TexUnit unit : texUnits.values()) {
            for (Texture tex : unit.towns) {
                tex.dispose();
            }
            for (Texture tex : unit.heroes) {
                tex.dispose();
            }
            for (Texture tex : unit.combatants) {
                tex.dispose();
            }
        }
        texUnits = new HashMap<>();
    }

    public void loadTiles() {
        File[] tilesFiles = new File("tiles/").listFiles();
        if (tilesFiles != null && tilesFiles.length > 0) {
            for (File file : tilesFiles) {
                tiles.put(file.getName().substring(0, file.getName().indexOf(".")), new Texture("tiles/" + file.getName()));
            }
        }
    }

    public TexLoader loadUnit(String unitName) {
        Sort sort = new Sort();
        File unitFolder = new File("texUnits/" + unitName);
        if (!texUnits.containsKey(unitName))
            texUnits.put(unitName, new TexUnit(unitName));
        File[] townsFiles = new File(unitFolder.getPath() + "/towns/").listFiles();
        if (townsFiles != null && townsFiles.length > 0) {
            sort.sort(townsFiles);
            for (File file : townsFiles)
                texUnits.get(unitName).towns.add(new Texture(file.getPath()));
        }
        File[] heroesFiles = new File(unitFolder.getPath() + "/heroes/").listFiles();
        if (heroesFiles != null && heroesFiles.length > 0) {
            sort.sort(heroesFiles);
            for (File file : heroesFiles)
                texUnits.get(unitName).heroes.add(new Texture(file.getPath()));
        }
        File[] combatantsFiles = new File(unitFolder.getPath() + "/combatants/").listFiles();
        if (combatantsFiles != null && combatantsFiles.length > 0) {
            sort.sort(combatantsFiles);
            for (File file : combatantsFiles)
                texUnits.get(unitName).combatants.add(new Texture(file.getPath()));
        }
        File namesFile = new File(unitFolder.getPath()+"/names");
        if(namesFile.exists()){
            try {
                Scanner scan = new Scanner(namesFile);
                String[] values = scan.nextLine().split("\\s\\s+");
                Collections.addAll(texUnits.get(unitName).names1, values);
                values = scan.nextLine().split("\\s\\s+");
                Collections.addAll(texUnits.get(unitName).names2, values);
                values = scan.nextLine().split("\\s\\s+");
                Collections.addAll(texUnits.get(unitName).names3, values);
            } catch (FileNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
        return this;
    }
}
