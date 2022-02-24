package com.volbot.lourts.GUI;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.GUI.abstracts.GameWindow;
import com.volbot.lourts.Main;

public class HUD extends GameWindow {


    public HUD() {
        buttons = new Button[1];
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/hud/buttons/partybutton.png"))); // texture needs lots of refinement
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/hud/buttons/partybuttondown.png")));
        buttons[0] = new Button(buttonStyle);
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        super.drawMenu(batch, cam);
        buttons[0].setWidth(0.085f * cam.viewportWidth);
        buttons[0].setHeight(0.09f * cam.viewportHeight);
        buttons[0].setPosition(0.915f * cam.viewportWidth, -0.03f * cam.viewportHeight);
    }

    @Override
    public void activateButton(int buttonDex) {
        switch(buttonDex) {
            case 0:
                Main.gui.drawPartyMenu();
                break;
        }
        super.activateButton(buttonDex);
    }
}


