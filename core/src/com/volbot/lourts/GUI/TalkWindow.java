package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Data.Battle;
import com.volbot.lourts.Data.TalkResponse;
import com.volbot.lourts.GUI.abstracts.InteractWindow;
import com.volbot.lourts.Main;


public class TalkWindow extends InteractWindow {

    TextButton.TextButtonStyle buttonStyle;

    public TalkWindow(Agent a, int intent) {
        entity = a;
        entityname = a instanceof Location ?
                (((Location) a).heroes.contains(((Location) a).getFigurehead()) ?
                        ((Location) a).getFigurehead().getName() : a.getName() + " Representative")
                : a.getName();
        conversation = intent == 0 ? a.startConversation(Main.player) : a.startCombat(Main.player);

        windowbg = new Texture("GUI/windows/menublank.png");

        buttons = new TextButton[4];

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = new BitmapFont();

        genButtonText();
    }

    public TalkWindow(Agent a, boolean playerWon) {
        entity = a;
        entityname = a instanceof Location ?
                (((Location) a).heroes.contains(((Location) a).getFigurehead()) ?
                        ((Location) a).getFigurehead().getName() : a.getName() + " Representative")
                : a.getName();
        conversation = a.endCombat(Main.player, playerWon);

        windowbg = new Texture("GUI/windows/menublank.png");

        buttons = new TextButton[4];

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = new BitmapFont();

        genButtonText();
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        float widthinc = windowbg.getWidth() * 0.05f;
        float heightinc = windowbg.getHeight() * 0.05f;
        if(conversation != null) {
            if (conversation.options.length > 4) {
            } else {
                float temp = (cam.viewportWidth - windowbg.getWidth()) / 2 + widthinc / 2;
                buttons[0].setX(temp);
                buttons[2].setX(temp);

                temp += buttons[0].getWidth() + widthinc;
                buttons[1].setX(temp);
                buttons[3].setX(temp);

                temp = (cam.viewportHeight - windowbg.getHeight()) / 2 + heightinc;
                buttons[2].setY(temp);
                buttons[3].setY(temp);

                temp += buttons[2].getHeight() + heightinc;
                buttons[0].setY(temp);
                buttons[1].setY(temp);
            }
        }
        super.drawMenu(batch, cam);
    }

    @Override
    public void activateButton(int buttonDex) {
        boolean reset = false;
        switch (conversation.options[buttonDex].option.charAt(0)) {
            case '+':
                entity.rep.impress(Main.player, 1);
                break;
            case 'U':
                break;
            case '-':
                entity.rep.impress(Main.player, -1);
                break;
            case 'F':
                Main.GAMEMODE = 1;
                Main.battle = new Battle(Main.player, entity);
                break;
            case 'R':
                int num = 0;
                int numDex = 2;
                String option = conversation.options[buttonDex].option;
                while (!Character.isDigit(option.charAt(numDex))) {
                    numDex++;
                }
                while (Character.isDigit(option.charAt(numDex))) {
                    num *= 10;
                    num += Character.getNumericValue(option.charAt(numDex));
                    numDex++;
                }
                Location loc = (Location) entity;
                Main.player.getParty().add(loc.recruit(num));
                break;
            case 'M':
                resetConvo();
                reset = true;
        }
        buttons[buttonDex].setChecked(false);
        if (!reset) {
            advanceConversation(buttonDex);
        }
    }

    public void genButtonText() {
        float buttonwidth = windowbg.getWidth() * 0.45f;
        if (conversation == null) return;
        for (int i = 0; i < Math.max(conversation.options.length, 4); i++) {
            TextButton temp;
            if (i >= conversation.options.length || conversation.options[i] == null) {
                temp = new TextButton("", buttonStyle);
            } else {
                temp = new TextButton(conversation.options[i].option.substring(2), buttonStyle);
            }
            temp.setWidth(buttonwidth);
            temp.getLabel().setWrap(true);
            buttons[i] = temp;
        }
    }

    public void resetConvo() {
        TalkResponse newConvo = entity.startConversation(Main.player);
        newConvo.response = "Is there anything else I can help you with?";
        conversation = newConvo;
        genButtonText();
    }

    public void advanceConversation(int i) {
        conversation = conversation.options[i].response;
        genButtonText();
    }
}
