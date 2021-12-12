package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TalkWindow extends GameWindow {

    public TalkWindow() {
        windowbg=new Texture("GUI/menublank");
    }

    @Override
    public void drawMenu(SpriteBatch batch, Camera cam) {
        batch.draw(windowbg, (int)(windowbg.getWidth()/2), (int)(windowbg.getHeight()/2));
    }
}
