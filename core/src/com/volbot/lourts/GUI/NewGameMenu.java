package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Location;
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

    private int texID = 0;

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        super.drawMenu(batch, cam);
        if (buttons == null) {
            buttons = new Button[4];
            buttons[0] = new TextButton("Start", buttonStyle);
            buttons[1] = new TextButton("Back", buttonStyle);
            Button.ButtonStyle tempStyle = new Button.ButtonStyle();
            tempStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/talkbutton.png")));
            tempStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/talkbuttondown.png")));
            buttons[2] = new Button(tempStyle);
            buttons[3] = new Button(tempStyle);
        }
        batch.draw(Main.texLoader.texUnits.get("base").heroes.get(this.texID),
                (cam.viewportWidth - windowbg.getWidth()) / 2 + windowbg.getWidth() * 0.05f,
                windowbg.getHeight() * 0.66f,
                200, 200);
        buttons[2].setY(windowbg.getHeight() * 0.63f);
        buttons[3].setY(windowbg.getHeight() * 0.63f);
        buttons[2].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+20);
        buttons[3].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+200);
        float temp = (cam.viewportWidth - buttons[0].getWidth()) / 2;
        for (int i = 0; i < 2; i++) buttons[i].setX(temp);
        temp = cam.viewportHeight / 2.6f;
        for (int i = 0; i < 2; i++) buttons[i].setY(temp -= buttons[i].getHeight());
    }

    @Override
    public void activateButton(int buttonDex) {
        switch (buttonDex) {
            case 0:

            case 1:
                Main.gui.currmenu = new MainMenu();
                break;
            case 2:
                texID -= (texID > 0 ? 1 : 0);
                break;
            case 3:
                int len = Main.texLoader.texUnits.get("base").heroes.size();
                texID += (texID < (len-1) ? 1 : 0);
                break;
        }
        buttons[buttonDex].setChecked(false);
    }
}
