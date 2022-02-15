package com.volbot.lourts.Saves;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Main;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class SaveManager {

    public SaveManager() {

    }

    public void loadSave(File file) {

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
            for(Agent a : Main.entities) print.println(a.getName()+"   "+a.position.x+"  "+a.position.y);
            print.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
