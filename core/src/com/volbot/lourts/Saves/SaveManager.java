package com.volbot.lourts.Saves;

import com.volbot.lourts.Agents.*;
import com.volbot.lourts.Data.Reputation;
import com.volbot.lourts.Main;
import com.volbot.lourts.Map.GameMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SaveManager {

    public SaveManager() {

    }

    public void loadSave(File saveFolder) {
        Main.map = Main.worldmap;
        if (saveFolder.exists() && saveFolder.isDirectory()) {
            File saveMaster = new File(saveFolder.getPath() + "/main");
            if (saveMaster.exists()) {
                try {
                    Scanner saveScanner = new Scanner(saveMaster);
                    String[] values = saveScanner.nextLine().split("\\s\\s+");
                    Main.player = new Individual(values[0]);
                    Main.player.position.set(Float.parseFloat(values[1]), Float.parseFloat(values[2]), 0);
                    Main.player.theme = values[3];
                    Main.player.texID = Integer.parseInt(values[4]);
                    Main.entities.add(Main.player);
                    Main.cam.position.set(Float.parseFloat(values[5]), Float.parseFloat(values[6]), 0);
                    Main.worldmap = new GameMap(saveFolder.getName(),values[7]);
                    while (saveScanner.hasNextLine()) {
                        values = saveScanner.nextLine().split("\\s\\s+");
                        Location loc = new Location(values[0], values[6], (int) Double.parseDouble(values[1]), (int) Double.parseDouble(values[2]), Integer.parseInt(values[5]));
                        loc.getFigurehead().position.set(Float.parseFloat(values[7]), Float.parseFloat(values[8]), 0);
                        loc.getFigurehead().theme = values[9];
                        loc.getFigurehead().texID = Integer.parseInt(values[10]);
                        loc.heroes = new ArrayList<>();
                        loc.getFigurehead().location = Main.findLoc(values[11]);
                        if(loc.getFigurehead().location==null) {
                            Main.entities.add(loc.getFigurehead());
                        } else {
                            loc.getFigurehead().location.heroes.add(loc.getFigurehead());
                        }
                        Main.locations.add(loc);
                    }
                    saveScanner.close();
                    for (Agent a : Main.entities) {
                        readHero(a,saveFolder);
                    }
                    for (Location l : Main.locations) {
                        for (Agent a : l.heroes) {
                            readHero(a, saveFolder);
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void saveGame() {
        File[] saves = new File("saves/").listFiles();
        File saveFolder = null;
        String playerName = Main.player.getName();
        if (saves != null && saves.length > 0) {
            for (File save : saves) {
                if (save.isDirectory() && save.getName().contains(playerName)) {
                    saveFolder = save;
                }
            }
        }
        if (saveFolder == null) {
            saveFolder = new File("saves/" + Main.map.name + "/");
            try {
                if (saveFolder.mkdir()) {
                    System.out.println("FILE CREATED");
                }
            } catch (SecurityException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        File saveMaster = new File(saveFolder.getPath() + "/main");
        try {
            if (saveMaster.createNewFile()) {
                System.out.println("FILE CREATED");
            }
            PrintStream print = new PrintStream(saveMaster);
            for (Individual a : Main.entities) {
                if (a.equals(Main.player)) {
                    print.print(a.getName() + "\t\t" + a.position.x + "\t\t" + a.position.y + "\t\t");
                    print.print(a.theme + "\t\t" + a.texID + "\t\t");
                    print.print(Main.cam.position.x + "\t\t" + Main.cam.position.y + "\t\t" + Main.map.SEED + "\t\t");
                    print.print("\n");
                }
            }
            for (Location a : Main.locations) {
                print.print(a.getName() + "\t\t" + a.position.x + "\t\t" + a.position.y + "\t\t");
                print.print(a.theme + "\t\t" + a.texID + "\t\t");
                print.print(a.getPopulationSize() + "\t\t" +
                        a.getFigurehead().getName() + "\t\t" +
                        a.getFigurehead().position.x + "\t\t" +
                        a.getFigurehead().position.y + "\t\t" +
                        a.getFigurehead().theme + "\t\t" +
                        a.getFigurehead().texID + "\t\t" +
                        (a.getFigurehead().location!=null?a.getFigurehead().location.getName():"null") + "\t\t");
                print.print("\n");
            }
            print.close();
            for (Agent a : Main.entities) {
                writeHero(a, saveFolder);
            }
            for (Location l : Main.locations) {
                for (Agent a : l.heroes) {
                    writeHero(a, saveFolder);
                }
            }
            print.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void writeHero(Agent a, File saveFolder) {
        try {
            File folder = new File(saveFolder.getPath() + "/" + a.getName());
            if (folder.mkdir()) {
                System.out.println("FILE CREATED");
            }
            File repFile = new File(folder.getPath() + "/rep");
            if (repFile.createNewFile()) {
                System.out.println("FILE CREATED");
            }
            PrintStream print = new PrintStream(repFile);
            Reputation rep = a.rep;
            for (Individual inn : rep.known()) {
                print.println(inn.getName() + "\t\t" + rep.get(inn));
            }
            print.close();
            File popFile = new File(folder.getPath() + "/pop");
            if (popFile.createNewFile()) {
                System.out.println("FILE CREATED");
            }
            print = new PrintStream(popFile);
            Population pop = a.getParty();
            for (Demographic d : pop.pop) {
                print.println(d.getOrigin().getName() + "\t\t" + d.getPopulation() + "\t\t" + d.getLevel());
            }
            print.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void readHero(Agent a, File saveFolder) {
        try {
            File folder = new File(saveFolder.getPath() + "/" + a.getName());
            Scanner saveScanner;
            String[] values;
            if (folder.exists()) {
                File repFile = new File(folder.getPath() + "/rep");
                if (repFile.exists()) {
                    Reputation rep = a.rep;
                    saveScanner = new Scanner(repFile);
                    while (saveScanner.hasNextLine()) {
                        values = saveScanner.nextLine().split("\\s\\s+");
                        Agent meetguy = Main.findHero(values[0]);
                        if (meetguy != null) {
                            rep.meet(meetguy);
                            rep.impress(meetguy, Integer.parseInt(values[1]));
                        } else {
                            meetguy = Main.findLoc(values[0]);
                            if (meetguy != null) {
                                rep.meet(meetguy);
                                rep.impress(meetguy, Integer.parseInt(values[1]));
                            }
                        }
                    }
                    saveScanner.close();
                }
                File popFile = new File(folder.getPath() + "/pop");
                if (popFile.exists()) {
                    Population pop;
                    saveScanner = new Scanner(popFile);
                    pop = new Population();
                    while (saveScanner.hasNextLine()) {
                        values = saveScanner.nextLine().split("\\s\\s+");
                        Location loc = Main.findLoc(values[0]);
                        if(loc!=null){
                            pop.add(new Demographic(loc,Integer.parseInt(values[1]),Integer.parseInt(values[2])));
                        }
                    }
                    a.setParty(pop);
                    saveScanner.close();
                }
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
            return;
        }
    }
}
