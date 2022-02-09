package com.volbot.lourts.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Main;

public class MainMenu extends GameWindow {

    TextButton.TextButtonStyle buttonStyle;

    public MainMenu() {
        windowbg = new Texture("GUI/windows/menublank.png");
        buttons=new Button[4];
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = new BitmapFont();
        buttons[0]=new TextButton("New Game",buttonStyle);
        buttons[1]=new TextButton("Load Game",buttonStyle);
        buttons[2]=new TextButton("Settings",buttonStyle);
        buttons[3]=new TextButton("Quit",buttonStyle);
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        float temp=(cam.viewportWidth-buttons[0].getWidth())/2;
        for(Button b : buttons)b.setX(temp);
        temp=cam.viewportHeight/1.3f;
        for(Button b : buttons)b.setY(temp-=(cam.viewportHeight/1.5f)/buttons.length);
        super.drawMenu(batch, cam);
    }

    @Override
    public void activateButton(int buttonDex) {
        switch(buttonDex) {
            case 0: //new game
                Main.initDemo();
                Main.GAMEMODE=0;
                Main.gui.currmenu=null;
                break;
            case 1: //load game
                break;
            case 2: //settings
                break;
            case 3: //quit
                Gdx.app.exit();
                break;
        }
    }
}
