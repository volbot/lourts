package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.Data.TalkResponse;
import com.volbot.lourts.Main;

public abstract class InteractWindow extends GameWindow {
    protected Agent entity;
    protected BitmapFont font = new BitmapFont();
    public TalkResponse conversation;
    protected Integer repInt;

    @Override
    public void drawMenu(SpriteBatch batch, Camera cam) {
        super.drawMenu(batch, cam);
        drawAgentInfo(batch,cam);
    }

    protected void drawAgentInfo(SpriteBatch batch, Camera cam) {
        int xleft = (int)(cam.viewportWidth-windowbg.getWidth())/2;
        int ybot = (int)(cam.viewportHeight-windowbg.getHeight())/2;
        int scalefac = 10;
        int texID = entity instanceof Location ? ((Location) entity).getFigurehead().texID : entity.texID;
        batch.draw(Main.texLoader.heroes.get(texID), xleft+windowbg.getWidth()*0.05f, ybot+windowbg.getHeight()*0.5f, 20*scalefac, 20*scalefac);
        font.getData().setScale(2f);
        if(repInt==null){
            repInt = entity.rep.get(Main.player);
        }
        String repString = entity.rep.knows(Main.player) ? ""+repInt : "JUST MET";
        GlyphLayout layout = new GlyphLayout(font, entity.getName() + "\n" +
                                                        "FACTION: n/a" + "\n" +
                                                        "REP: " + repString + "\n" +
                                                        "PARTY: 1/1");
        font.draw(batch, layout, (xleft+windowbg.getWidth()*0.43f), (ybot+windowbg.getHeight()*.93f));
        drawResponse(batch,cam);
    }

    public void drawResponse(SpriteBatch batch, Camera cam) {
        int xleft = (int)(cam.viewportWidth-windowbg.getWidth())/2;
        int ybot = (int)(cam.viewportHeight-windowbg.getHeight())/2;
        font.getData().setScale(1f);
        GlyphLayout layout = new GlyphLayout(font,
                conversation.response, Color.WHITE,
                windowbg.getWidth()*0.5f, Align.topLeft, true
        );
        font.draw(batch, layout, (xleft+windowbg.getWidth()*0.43f), (ybot+windowbg.getHeight()*.6f));
    }
}