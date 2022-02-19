package com.volbot.lourts.GUI;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Agents.Demographic;
import com.volbot.lourts.Data.Battle;
import com.volbot.lourts.Data.TalkResponse;
import com.volbot.lourts.Main;

import java.awt.*;

public class GUIManager {

    public GameMenu currmenu = null;
    public HUD hud = new HUD();

    public GUIManager() {

    }

    public void loop() {
        if (currmenu == null) {
            for (int i = 0; i < hud.buttons.length; i++) {
                if (hud.buttons[i].isChecked()) {
                    hud.activateButton(i);
                }
            }
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
            }
        } else if (currmenu instanceof NotificationWindow || currmenu instanceof MainMenu || currmenu instanceof PauseMenu) {
            GameWindow menu = (GameWindow) currmenu;
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
                    menu.activateButton(i);
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

    public void drawPauseMenu() {
        Main.setPaused(true);
        currmenu = new PauseMenu();
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
