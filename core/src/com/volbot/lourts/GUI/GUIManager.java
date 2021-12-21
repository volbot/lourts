package com.volbot.lourts.GUI;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Agents.Demographic;
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
                        case 'R':
                            int num = 0;
                            int numDex = 2;
                            String option = menu.conversation.options[i].option;
                            while(!Character.isDigit(option.charAt(numDex))){
                                numDex++;
                            }
                            while(Character.isDigit(option.charAt(numDex))){
                                num*=10;
                                num+=Character.getNumericValue(option.charAt(numDex));
                                numDex++;
                            }
                            Location loc = (Location)menu.entity;
                            loc.setPopulation(loc.getPopulation()-num);
                            Main.player.getParty().add(new Demographic(loc,num));
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
        currmenu=new TalkWindow(a);
    }

    public void clearMenu() {
        currmenu = null;
    }
}
