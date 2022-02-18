package com.volbot.lourts.Saves;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Faction;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class SaveManager {

    public SaveManager() {

    }

    public void loadSave(File saveFolder) {
        Main.map=Main.worldmap;
        if(saveFolder.exists()&&saveFolder.isDirectory()){
            File saveMaster = new File(saveFolder.getPath()+"/main");
            if(saveMaster.exists()&&!saveMaster.isDirectory()){
                try {
                    Scanner saveScanner = new Scanner(saveMaster);
                    String[] values = saveScanner.nextLine().split("\\s\\s+");
                    System.out.println(Arrays.toString(values));
                    Main.player = new Individual(values[0]);
                    Main.player.position.set(Float.parseFloat(values[1]),Float.parseFloat(values[2]),0);
                    Main.player.theme = values[3];
                    Main.player.texID = Integer.parseInt(values[4]);
                    Main.entities.add(Main.player);
                    while(saveScanner.hasNextLine()){
                        values = saveScanner.nextLine().split("\\s\\s+");
                        Location loc = new Location(values[0], values[6], (int)Double.parseDouble(values[1]), (int)Double.parseDouble(values[2]), Integer.parseInt(values[5]));
                        loc.getFigurehead().position.set(Float.parseFloat(values[7]),Float.parseFloat(values[8]),0);
                        loc.getFigurehead().theme=values[9];
                        loc.getFigurehead().texID=Integer.parseInt(values[10]);
                        Main.entities.add(loc);
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
            saveFolder = new File("saves/" + playerName + "/");
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
            int i = 0;
            for(Agent a : Main.entities) {
                i++;
                if(a instanceof Location || i == 1){
                    print.print(a.getName()+"\t\t"+a.position.x+"\t\t"+a.position.y+"\t\t");
                    print.print(a.theme+"\t\t"+a.texID+"\t\t");
                    if(a instanceof Location) {
                        print.print(a.getPopulationSize()+"\t\t"+
                                ((Location) a).getFigurehead().getName()+"\t\t"+
                                ((Location) a).getFigurehead().position.x+"\t\t"+
                                ((Location) a).getFigurehead().position.y+"\t\t"+
                                ((Location) a).getFigurehead().theme+"\t\t"+
                                ((Location) a).getFigurehead().texID+"\t\t");
                    }
                    print.print("\n");
                }
            }
            print.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
