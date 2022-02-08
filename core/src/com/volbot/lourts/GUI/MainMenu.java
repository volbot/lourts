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

public class MainMenu extends GameMenu{

    TextButton.TextButtonStyle buttonStyle;

    public MainMenu() {
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

    public void menuAction(int buttonDex){
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

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        float yInc = buttons[0].getY();
        for(Button b : buttons) {
            b.setX((cam.viewportWidth/2)-(b.getWidth()/2));
            b.setY(yInc);
            yInc+=(b.getHeight()+15);
        }
        super.drawMenu(batch, cam);
    }
}
