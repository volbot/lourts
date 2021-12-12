package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Main;

public abstract class InteractWindow extends GameWindow {
    protected Agent entity;
    protected BitmapFont font = new BitmapFont();

    @Override
    public void drawMenu(SpriteBatch batch, Camera cam) {
        super.drawMenu(batch, cam);
        drawAgentInfo(batch,cam);
    }

    protected void drawAgentInfo(SpriteBatch batch, Camera cam) {
        int xleft = (int)(cam.viewportWidth-windowbg.getWidth())/2;
        int ybot = (int)(cam.viewportHeight-windowbg.getHeight())/2;
        int scalefac = 10;
        batch.draw(Main.texLoader.heroes.get(1), xleft+windowbg.getWidth()*0.05f, ybot+windowbg.getHeight()*0.5f, 20*scalefac, 20*scalefac);
        font.getData().setScale(2f);
        GlyphLayout layout = new GlyphLayout(font, entity.getName() + "\n" +
                                                        "FACTION: n/a" + "\n" +
                                                        "YOU JUST MET" + "\n" +
                                                        "PARTY: 1/1");
        font.draw(batch, layout, (xleft+windowbg.getWidth()*0.43f), (ybot+windowbg.getHeight()*.93f));
    }
}