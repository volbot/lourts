package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Agent;

public class InteractMenu {
    Agent entity;
    public Button[] buttons = new Button[2];

    public InteractMenu(Agent entity){
        this.entity=entity;
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/talkbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/talkbuttondown.png")));
        buttons[0]=new Button(buttonStyle);
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();
        buttonStyle2.up=new TextureRegionDrawable(new TextureRegion(new Texture("GUI/fightbutton.png")));
        buttonStyle2.checked=new TextureRegionDrawable(new TextureRegion(new Texture("GUI/fightbuttondown.png")));
        buttons[1]=new Button(buttonStyle2);
    }

    public void drawButtons(SpriteBatch batch, Camera cam){
        buttons[0].setX(cam.position.x+entity.x-25-10);
        buttons[0].setY(cam.position.y+entity.y-10);
        buttons[1].setX(cam.position.x+entity.x+25-10);
        buttons[1].setY(cam.position.y+entity.y-10);

        buttons[0].draw(batch,1);
        buttons[1].draw(batch,1);
    }

    public Agent getAgent() {
        return entity;
    }
}
