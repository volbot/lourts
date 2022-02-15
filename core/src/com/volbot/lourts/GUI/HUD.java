package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HUD extends GameWindow {

    public HUD() {
        buttons=new Button[1];
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/hud/buttons/partybutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/hud/buttons/partybuttondown.png")));
        buttons[0]=new Button(buttonStyle);
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        super.drawMenu(batch, cam);
    }

    @Override
    public void activateButton(int buttonDex) {

    }
}