package com.volbot.lourts.GUI.abstracts;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public abstract class GameWindow extends GameMenu {

    protected TextButton.TextButtonStyle buttonStyle;

    public Texture windowbg = null;

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        if (windowbg != null)
            batch.draw(windowbg, (cam.viewportWidth - windowbg.getWidth()) / 2, (cam.viewportHeight - windowbg.getHeight()) / 2);
        super.drawMenu(batch, cam);
    }

    public void activateButton(int buttonDex) {
        buttons[buttonDex].setChecked(false);
    }
}
