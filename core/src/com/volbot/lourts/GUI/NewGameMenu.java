package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.GUI.abstracts.GameMenu;
import com.volbot.lourts.GUI.abstracts.GameWindow;
import com.volbot.lourts.Main;

public class NewGameMenu extends GameWindow {
    public NewGameMenu() {
        super();
        windowbg = new Texture("GUI/windows/menublank.png");
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = new BitmapFont();
        buttons = null;
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        super.drawMenu(batch,cam);
        if(buttons==null){
            buttons = new Button[2];
            buttons[0] = new TextButton("Start",buttonStyle);
            buttons[1] = new TextButton("Back",buttonStyle);
        }
        float temp = (cam.viewportWidth - buttons[0].getWidth()) / 2;
        for (Button b : buttons) b.setX(temp);
        temp = cam.viewportHeight / 2.5f;
        for (Button b : buttons) b.setY(temp -= b.getHeight());
    }

    @Override
    public void activateButton(int buttonDex) {
        switch(buttonDex){
            case 0:

            case 1:
                Main.gui.currmenu = new MainMenu();
                break;
        }
    }
}
