package com.volbot.lourts.GUI;

import com.volbot.lourts.Agents.Agent;

public class GUIManager {

    public GameMenu currmenu = null;

    public GUIManager() {

    }

    public void drawInteractMenu(Agent a) {
        currmenu = new InteractMenu(a);
    }

    public void clearInteractMenu() {
        currmenu=null;
    }
}
