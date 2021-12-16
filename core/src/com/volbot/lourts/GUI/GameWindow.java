package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameWindow extends GameMenu{

    public Texture windowbg;

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        batch.draw(windowbg, (cam.viewportWidth-windowbg.getWidth())/2, (cam.viewportHeight-windowbg.getHeight())/2);
        super.drawMenu(batch,cam);
    }
}
