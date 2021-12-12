package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Agent;

public class TalkWindow extends InteractWindow {

    public TalkWindow(Agent a) {
        entity = a;
        windowbg=new Texture("GUI/windows/menublank.png");
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));

        buttons = new Button[4];

        for(int i = 0; i<4; i++) buttons[i]=new Button(buttonStyle);
    }

    @Override
    public void drawMenu(SpriteBatch batch, Camera cam) {
        float widthinc = windowbg.getWidth()*0.05f;
        float heightinc = windowbg.getHeight()*0.05f;

        float temp = (cam.viewportWidth-windowbg.getWidth())/2 + widthinc;
        buttons[0].setX(temp);
        buttons[2].setX(temp);

        temp += buttons[0].getWidth()+widthinc;
        buttons[1].setX(temp);
        buttons[3].setX(temp);

        temp = (cam.viewportHeight-windowbg.getHeight())/2 + heightinc;
        buttons[2].setY(temp);
        buttons[3].setY(temp);

        temp += buttons[2].getHeight()+heightinc;
        buttons[0].setY(temp);
        buttons[1].setY(temp);
        super.drawMenu(batch,cam);
    }
}
