package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Demographic;
import com.volbot.lourts.Agents.Population;
import com.volbot.lourts.GUI.abstracts.GameWindow;
import com.volbot.lourts.Main;

import java.util.ArrayList;
import java.util.Arrays;

public class PartyWindow extends GameWindow {

    private TextButton[] combatants;

    public PartyWindow() {
        windowbg = new Texture("GUI/windows/menublank.png");

        buttons = new TextButton[3];
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = new BitmapFont();

        buttons[0] = new TextButton("Train", buttonStyle);
        buttons[1] = new TextButton("Disband", buttonStyle);
        buttons[2] = new TextButton("Back", buttonStyle);

        for (int i = 0; i < 3; i++) {
            buttons[i].setY(windowbg.getHeight() * 0.13f);
            buttons[i].setWidth(windowbg.getWidth() * 0.3f);
        }

        buttons[0].setX(0);
        buttons[1].setX(buttons[0].getWidth());
        buttons[2].setX(buttons[0].getWidth()*2);

        Population party = Main.player.getParty();

        combatants = new TextButton[party.pop.size()];
        for(int i = 0; i < combatants.length; i++){
            combatants[i]=new TextButton(party.pop.get(i).getName(),buttonStyle);
            combatants[i].setWidth(windowbg.getWidth() * 0.2f);
            combatants[i].setHeight(buttons[0].getHeight() * 0.3f);
        }
    }

    @Override
    public void activateButton(int buttonDex) {
        switch (buttonDex) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                Main.gui.clearMenu();
                break;
        }
        super.activateButton(buttonDex);
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        super.drawMenu(batch, cam);

        buttons[0].setX((cam.viewportWidth - windowbg.getWidth()) / 2);
        buttons[1].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+buttons[0].getWidth());
        buttons[2].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+(2*buttons[0].getWidth()));

        if(combatants.length>0) {
            float listLen = 0f;
            combatants[0].setY(windowbg.getHeight() * 0.93f);
            combatants[0].setX(buttons[2].getX());
            listLen += combatants[0].getHeight();
            combatants[0].draw(batch, 1.0f);
            for (int i = 1; i < combatants.length; i++) {
                combatants[i].setX(buttons[2].getX());
                combatants[i].setY(combatants[i - 1].getHeight() - combatants[0].getHeight());
                listLen += combatants[0].getHeight();
                combatants[i].draw(batch, 1.0f);
                System.out.println(combatants[i].getLabel());
                if (listLen > windowbg.getHeight() * 0.6f) {
                    break;
                }
            }
        }
    }
}
