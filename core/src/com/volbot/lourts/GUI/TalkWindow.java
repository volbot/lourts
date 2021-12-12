package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class TalkWindow extends GameWindow {

    Texture windowbg;

    @Override
    public void drawMenu(SpriteBatch batch, Camera cam) {
        batch.draw(windowbg, (int)(windowbg.getWidth()/2), (int)(windowbg.getHeight()/2));
    }
}
