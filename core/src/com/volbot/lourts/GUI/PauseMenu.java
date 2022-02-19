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
import com.volbot.lourts.GUI.abstracts.GameWindow;
import com.volbot.lourts.Main;

import java.io.File;

public class PauseMenu extends GameWindow {

    TextButton.TextButtonStyle buttonStyle;
    private int menuType;

    public PauseMenu() {
        windowbg = null;
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = new BitmapFont();
        switchMenu(0);
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        float temp = (cam.viewportWidth - buttons[0].getWidth()) / 2;
        for (Button b : buttons) b.setX(temp);
        temp = cam.viewportHeight / 1.3f;
        for (Button b : buttons) b.setY(temp -= (cam.viewportHeight / 1.5f) / buttons.length);
        super.drawMenu(batch, cam);
    }

    @Override
    public void activateButton(int buttonDex) {
        switch (menuType) {
            case 0:
                switch (buttonDex) {
                    case 0: //save game
                        Main.saveMan.saveGame();
                        break;
                    case 1: //settings
                        break;
                    case 2: //main menu
                        Main.saveMan.saveGame();
                        Main.GAMEMODE=-1;
                        break;
                    case 3: //hard quit
                        Gdx.app.exit();
                        break;
                }
                break;
        }
        super.activateButton(buttonDex);
    }

    public void switchMenu(int menuDex) {
        menuType = menuDex;
        switch (menuDex) {
            case 0:
                buttons = new Button[4];
                buttons[0] = new TextButton("Save Game", buttonStyle);
                buttons[1] = new TextButton("Settings", buttonStyle);
                buttons[2] = new TextButton("Quit to Menu", buttonStyle);
                buttons[3] = new TextButton("Hard Quit", buttonStyle);
                break;
            case 1:
                File[] saveFolders = new File("saves/").listFiles();
                if (saveFolders != null) {
                    buttons = new Button[saveFolders.length + 1];
                    for (int i = 0; i < saveFolders.length; i++) {
                        buttons[i] = new TextButton(saveFolders[i].getName(), buttonStyle);
                    }
                    buttons[buttons.length - 1] = new TextButton("Back", buttonStyle);
                }
                break;
        }
    }
}
