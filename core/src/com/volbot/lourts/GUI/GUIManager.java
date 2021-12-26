package com.volbot.lourts.GUI;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Agents.Demographic;
import com.volbot.lourts.Main;

public class GUIManager {

    public GameMenu currmenu = null;

    public GUIManager() {

    }

    public void sendGUIMessage() {
        if (currmenu instanceof InteractMenu) {
            InteractMenu menu = (InteractMenu) currmenu;
            if (menu.buttons[0].isChecked()) {
                menu.buttons[0].setChecked(false);
                Main.gui.drawTalkMenu(menu.getAgent());
            } else if (menu.buttons[1].isChecked()) {
                menu.buttons[1].setChecked(false);
            }
        }
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
                    if (i >= menu.conversation.options.length) continue;
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
                            loc.setPopulation(loc.getPopulationInternal()-num);
                            Main.player.getParty().add(new Demographic(loc,num));
                    }

                    menu.buttons[i].setChecked(false);
                    menu.advanceConversation(i);
                }
            }
        }
    }

    public void drawInteractMenu(Agent a) {
        currmenu = new InteractMenu(a);
    }

    public void drawTalkMenu(Agent a) {
        Main.setPaused(true);
        currmenu=new TalkWindow(a);
    }

    public void clearMenu() {
        Main.setPaused(false);
        currmenu = null;
    }
}
