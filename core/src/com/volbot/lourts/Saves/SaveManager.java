package com.volbot.lourts.Saves;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;
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
                    Main.player.position.x = Float.parseFloat(values[1]);
                    String temp = values[2];
                    Main.player.position.y = Float.parseFloat(temp);
                    Main.player.theme = values[3];
                    Main.player.texID = Integer.parseInt(values[4]);
                    Main.entities.add(Main.player);
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
            for(Agent a : Main.entities) print.println(a.getName()+"    "+a.position.x+"    "+a.position.y+"   "+a.theme+"  "+a.texID);
            print.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
