package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.GUI.abstracts.GameMenu;

public class InteractMenu extends GameMenu {
    Agent entity;

    public InteractMenu(Agent entity){
        buttons=new Button[2];
        this.entity=entity;
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/talkbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/talkbuttondown.png")));
        buttons[0]=new Button(buttonStyle);
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();
        buttonStyle2.up=new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/fightbutton.png")));
        buttonStyle2.checked=new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/fightbuttondown.png")));
        buttons[1]=new Button(buttonStyle2);
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam){
        int texSize = entity instanceof Location ? 32 : 20;
        buttons[0].setX(cam.position.x+entity.position.x*cam.zoom-(texSize*1.2f*cam.zoom)-10*cam.zoom);
        buttons[0].setY(cam.position.y+entity.position.y*cam.zoom-10*cam.zoom);
        buttons[1].setX(cam.position.x+entity.position.x*cam.zoom+(texSize*1.2f*cam.zoom)-10*cam.zoom);
        buttons[1].setY(cam.position.y+entity.position.y*cam.zoom-10*cam.zoom);
        for(Button b : buttons){
            b.setWidth(20*cam.zoom);
            b.setHeight(20*cam.zoom);
        }
        super.drawMenu(batch,cam);
    }

    public Agent getAgent() {
        return entity;
    }
}
