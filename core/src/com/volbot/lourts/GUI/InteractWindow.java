package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Data.TalkResponse;
import com.volbot.lourts.Main;

public abstract class InteractWindow extends GameWindow {
    protected Agent entity;
    protected String entityname;
    protected BitmapFont font = new BitmapFont();
    public TalkResponse conversation;

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        super.drawMenu(batch, cam);
        drawAgentInfo(batch,cam);
    }

    protected void drawAgentInfo(SpriteBatch batch, Camera cam) {
        int xleft = (int)(cam.viewportWidth-windowbg.getWidth())/2;
        int ybot = (int)(cam.viewportHeight-windowbg.getHeight())/2;
        int scalefac = 10;
        int texID = entity instanceof Location ? ((Location) entity).getFigurehead().texID : entity.texID;
        batch.draw(Main.texLoader.texUnits.get(entity.theme).heroes.get(texID), xleft+windowbg.getWidth()*0.05f, ybot+windowbg.getHeight()*0.5f, 20*scalefac, 20*scalefac);
        font.getData().setScale(2f);
        int partySize = entity.getPopulation();
        String repString = entity.rep.knows(Main.player) ? ""+entity.rep.get(Main.player) : (entity instanceof Location ? "NEW HERE" : "JUST MET");
        GlyphLayout layout = new GlyphLayout(font, entityname + "\n" +
                                                        "FACTION: n/a" + "\n" +
                                                        "REP: " + repString + "\n" +
                                                        (entity instanceof Location ? "POP." : "PARTY")+": "+partySize);
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