package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Main;

public class TalkWindow extends InteractWindow {

    TextButton.TextButtonStyle buttonStyle;

    public TalkWindow(Agent a) {
        entity = a;
        entityname = a instanceof Location ?
                (((Location) a).heroes.contains(((Location) a).getFigurehead()) ?
                        ((Location) a).getFigurehead().getName() : a.getName()+" Representative")
                : a.getName();

        conversation = a.startConversation(Main.player);

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

        float temp = (cam.viewportWidth - windowbg.getWidth()) / 2 + widthinc/2;
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

        super.drawMenu(batch, cam);

    }

    public void genButtonText() {
        float buttonwidth = windowbg.getWidth() * 0.45f;
        for (int i = 0; i < 4; i++) {
            TextButton temp;
            if(conversation==null) return;
            if(i<conversation.options.length) {
                temp = new TextButton(conversation.options[i].option.substring(2), buttonStyle);
            } else {
                temp = new TextButton("",buttonStyle);
            }
            temp.setWidth(buttonwidth);
            temp.getLabel().setWrap(true);
            buttons[i] = temp;
        }
    }

    public void advanceConversation(int i) {
        conversation = conversation.options[i].response;
        genButtonText();
    }
}
