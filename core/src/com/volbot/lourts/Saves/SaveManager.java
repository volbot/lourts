package com.volbot.lourts.Saves;

import com.volbot.lourts.Main;

import java.io.File;
import java.io.IOException;

public class SaveManager {

    public SaveManager() {

    }

    public void loadSave(File file) {

    }

    public void saveGame() {
        File[] saves = new File("saves/").listFiles();
        File saveFolder = null;
        String playerName = Main.player.getName();
        if(saves!=null&&saves.length>0){
            for(File save : saves) {
                if(save.getName().contains(playerName)){
                    saveFolder = save;
                }
            }
        }
        if(saveFolder==null){
            saveFolder = new File("saves/"+playerName+"/");
            try {
                if(saveFolder.mkdir()){
                    System.out.println("FILE CREATED");
                }
            } catch(SecurityException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
