package com.volbot.lourts.GUI;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Main;

public class GUIManager {

    public GameMenu currmenu = null;

    public GUIManager() {

    }

    public void loop() {
        if(currmenu instanceof TalkWindow) {
            TalkWindow menu = (TalkWindow)currmenu;
            for(int i = 0; i < 4; i++) {
                if(menu.buttons[i].isChecked()){
                    switch(menu.talkOptions.get(i).charAt(0)){
                        case '+': menu.entity.rep.impress(Main.player,1); break;
                        case 'U': break;
                        case '-': menu.entity.rep.impress(Main.player,-1); break;
                    }
                    if(menu.talkOptions.get(i).charAt(1)=='E') clearMenu();
                    menu.buttons[i].setChecked(false);
                }
            }
        }
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
