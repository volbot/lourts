package com.volbot.lourts.GUI;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Hero;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.Main;

public class GUIManager {

    public GameMenu currmenu = null;

    public GUIManager() {

    }

    public void loop() {
        if (currmenu instanceof TalkWindow) {
            TalkWindow menu = (TalkWindow) currmenu;
            if (menu.conversation == null) {
                clearMenu();
                return;
            }
            for (int i = 0; i < 4; i++) {
                if (menu.buttons[i].isChecked()) {
                    if (i >= menu.conversation.options.length) break;
                    switch (menu.conversation.options[i].option.charAt(0)) {
                        case '+':
                            menu.entity.rep.impress(Main.player, 1);
                            break;
                        case 'U':
                            break;
                        case '-':
                            menu.entity.rep.impress(Main.player, -1);
                            break;
                    }
                    menu.advanceConversation(i);
                    menu.buttons[i].setChecked(false);
                }
            }
        }
    }

    public void drawInteractMenu(Agent a) {
        currmenu = new InteractMenu(a);
    }

    public void drawTalkMenu(Agent a) {
        if(a instanceof Location){
            Location loc = (Location) a;
            if(loc.heroes.contains(loc.getFigurehead())){
                currmenu = new TalkWindow(loc.getFigurehead());
            } else {
                currmenu = new TalkWindow(new Hero(loc.getName()+" Diplomat"));
            }
        } else {
            currmenu = new TalkWindow(a);
        }
    }

    public void clearMenu() {
        currmenu = null;
    }
}
