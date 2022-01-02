package com.volbot.lourts.GUI;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Agents.Demographic;
import com.volbot.lourts.Data.Battle;
import com.volbot.lourts.Data.TalkResponse;
import com.volbot.lourts.Main;

public class GUIManager {

    public GameMenu currmenu = null;

    public GUIManager() {

    }

    public void playerOpenWindow() {
        playerOpenWindow = true;
    }

    boolean playerOpenWindow = false;

    public void loop() {
        if (currmenu == null) {
            return;
        }
        if (currmenu instanceof InteractMenu) {
            InteractMenu menu = (InteractMenu) currmenu;
            if (Main.player.position.dst(menu.entity.position) < 30 || menu.entity.equals(Main.player.location)) {
                if (menu.buttons[0].isChecked()) {
                    menu.buttons[0].setChecked(false);
                    Main.gui.drawTalkMenu(menu.entity);
                } else if (menu.buttons[1].isChecked()) {
                    menu.buttons[1].setChecked(false);
                    Main.gui.drawCombatMenu(menu.entity);
                }
                playerOpenWindow = false;
            }
        } else if (currmenu instanceof NotificationWindow) {
            NotificationWindow menu = (NotificationWindow) currmenu;
            for (int i = 0; i < menu.buttons.length; i++) {
                if (menu.buttons[i].isChecked()) {
                    menu.activateButton(i);
                }
            }
        } else if (currmenu instanceof TalkWindow) {
            TalkWindow menu = (TalkWindow) currmenu;
            if (menu.conversation == null || menu.conversation.options == null) {
                clearMenu();
                return;
            }
            if (menu.buttons == null) return;
            for (int i = 0; i < menu.buttons.length; i++) {
                if (menu.buttons[i].isChecked()) {
                    if (i >= menu.conversation.options.length) continue;
                    menu.buttons[i].setChecked(false);
                    boolean reset = false;
                    switch (menu.conversation.options[i].option.charAt(0)) {
                        case '+':
                            menu.entity.rep.impress(Main.player, 1);
                            break;
                        case 'U':
                            break;
                        case '-':
                            menu.entity.rep.impress(Main.player, -1);
                            break;
                        case 'F':
                            Main.GAMEMODE = 1;
                            Main.battle = new Battle(Main.player, menu.entity);
                            break;
                        case 'R':
                            int num = 0;
                            int numDex = 2;
                            String option = menu.conversation.options[i].option;
                            while (!Character.isDigit(option.charAt(numDex))) {
                                numDex++;
                            }
                            while (Character.isDigit(option.charAt(numDex))) {
                                num *= 10;
                                num += Character.getNumericValue(option.charAt(numDex));
                                numDex++;
                            }
                            Location loc = (Location) menu.entity;
                            loc.setPopulation(loc.getPopulationInternal() - num);
                            Main.player.getParty().add(new Demographic(loc, num));
                            break;
                        case 'M':
                            menu.resetConvo();
                            reset = true;
                    }
                    menu.buttons[i].setChecked(false);
                    if (!reset) {
                        menu.advanceConversation(i);
                    }
                }
            }
        }

    }

    public void drawInteractMenu(Agent a) {
        currmenu = new InteractMenu(a);
    }

    public void drawTalkMenu(Agent a) {
        Main.setPaused(true);
        currmenu = new TalkWindow(a, 0);
    }

    public void drawTalkMenu(Agent a, boolean playerWon) {
        Main.setPaused(true);
        if (!a.rep.knows(Main.player)) {
            a.rep.meet(Main.player);
            Main.player.rep.meet(a);
        }
        currmenu = new TalkWindow(a, playerWon);
    }

    public void drawNotificationWindow(String type) {
        Main.setPaused(true);
        currmenu = new NotificationWindow(type);
    }

    public void drawCombatMenu(Agent a) {
        Main.setPaused(true);
        currmenu = new TalkWindow(a, 1);
    }

    public void clearMenu() {
        Main.setPaused(false);
        currmenu = null;
    }
}
