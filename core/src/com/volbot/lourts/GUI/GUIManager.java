package com.volbot.lourts.GUI;

import com.volbot.lourts.Agents.Agent;

public class GUIManager {

    public GameMenu currmenu = null;

    public GUIManager() {

    }

    public void loop() {

    }

    public void drawInteractMenu(Agent a) {
        currmenu = new InteractMenu(a);
    }

    public void drawTalkMenu(Agent a) {
        currmenu = new TalkWindow(a);
    }

    public void clearMenu() {
        currmenu=null;
    }
}
