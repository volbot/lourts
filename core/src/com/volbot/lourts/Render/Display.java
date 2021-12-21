package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.GUI.GameMenu;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.GUI.TalkWindow;
import com.volbot.lourts.Main;

import java.util.ArrayList;

public class Display {

    private final TexLoader texLoader;
    private final SpriteBatch batch;
    private final OrthographicCamera cam;

    private final BitmapFont font;

    public Display(OrthographicCamera camera) {
        cam = camera;
        texLoader = new TexLoader();
        batch = new SpriteBatch();
        font = new BitmapFont();
        batch.setProjectionMatrix(cam.combined);

    }

    public void loop() {
        float size = (20)*(cam.zoom);

        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        drawMap();
        for (Agent a : Main.entities) {
            if (a instanceof Individual) {
                if(a != Main.player) batch.draw(texLoader.texUnits.get(a.theme).heroes.get(a.texID), cam.position.x + a.x*cam.zoom - size/2, cam.position.y + a.y*cam.zoom - size/2, size, size);
            }
            if (a instanceof Location) {
                //System.out.println(a.x+"     "+a.y);
                batch.draw(texLoader.texUnits.get(a.theme).towns.get(a.texID), a.x*cam.zoom - size + cam.position.x, a.y*cam.zoom - size + cam.position.y , size*2, size*2);
            }
        }
        batch.draw(texLoader.texUnits.get(Main.player.theme).heroes.get(Main.player.texID), Main.player.x*cam.zoom - size/2 + cam.position.x, Main.player.y*cam.zoom - size/2 + cam.position.y, size, size);
        GameMenu tempMenu = Main.gui.currmenu;
        Agent hovered = Main.inputs.entityHovered(Main.inputs.getTouchPos());
        if (tempMenu != null) {
            if (tempMenu instanceof InteractMenu) {
                InteractMenu menu = (InteractMenu) tempMenu;
                drawName(menu.getAgent());
                menu.drawMenu(batch, cam);
                if (hovered != null && hovered != menu.getAgent()) {
                    drawName(hovered);
                    drawPopulation(hovered);
                }
            } else if(tempMenu instanceof TalkWindow) {
                TalkWindow menu = (TalkWindow) tempMenu;
                if(menu.conversation!=null) {
                    menu.drawMenu(batch, cam);
                } else {
                    Main.gui.clearMenu();
                }
            }
        } else {
            if (hovered != null) {
                drawName(hovered);
                drawPopulation(hovered);
            }
        }
        cam.update();
        batch.end();
    }


    public void drawName(Agent a) {
        font.getData().setScale(cam.zoom);
        GlyphLayout layout = new GlyphLayout(font, a.getName());
        int tempHeight = a instanceof Location ? 40 : 30;
        font.draw(batch, layout, cam.position.x + a.x*cam.zoom - layout.width / 2, cam.position.y + a.y*cam.zoom + tempHeight*cam.zoom);
    }

    public void drawPopulation(Agent a) {
        font.getData().setScale(cam.zoom);
        GlyphLayout layout = new GlyphLayout(font, ""+a.getPopulation());
        int tempHeight = a instanceof Location ? 25 : 15;
        font.draw(batch, layout, cam.position.x + a.x*cam.zoom - layout.width / 2, cam.position.y + a.y*cam.zoom - tempHeight*cam.zoom);
    }

    private void drawChunk(int chunkx, int chunky) {
        float size = (20)*(cam.zoom);
        float posx = (chunkx * (size*24)) + (int) cam.position.x;
        float posy;
        for (int drawx = 0; drawx < 24; drawx++) {
            posy = (chunky * (size*24)) + (int) cam.position.y;
            for (int drawy = 0; drawy < 24; drawy++) {
                batch.draw(
                        texLoader.tiles.get(
                                Main.map.chunks
                                        .get(chunkx)
                                        .get(chunky)
                                        [drawx][drawy]),
                        drawx * size + posx, drawy * size + posy,size,size
                );
            }
        }
    }

    private void drawMap() {
        float size = (20)*(cam.zoom);
        Vector3 camPosAbs = new Vector3(Math.abs(cam.position.x),Math.abs(cam.position.y),0);
        int camxstart = (int) Math.floor(camPosAbs.x);
        int camystart = (int) Math.floor(camPosAbs.y);
        int camxend = (int) Math.ceil(camPosAbs.x + cam.viewportWidth);
        int camyend = (int) Math.ceil(camPosAbs.y + cam.viewportHeight);
        ArrayList<Integer> chunksx = new ArrayList<>();
        ArrayList<Integer> chunksy = new ArrayList<>();
        chunksx.add((int) Math.floor(camxstart / (24*size))-1);
        chunksx.add((int) Math.ceil(camxend / (24*size))+1);int i = chunksx.get(0);
        while (i < chunksx.get(1)) {
            chunksx.add(i);
            i++;
        }
        chunksy.add((int) Math.floor(camystart / (24*size))-1);
        chunksy.add((int) Math.ceil(camyend / (24*size))+1);
        i = chunksy.get(0);
        while (i < chunksy.get(1)) {
            chunksy.add(i);
            i++;
        }
        for (int chunkx : chunksx) {
            if (chunkx >= Main.map.chunks.size() || chunkx < 0) {
                continue;
            }
            for (int chunky : chunksy) {
                if (chunky >= Main.map.chunks.get(chunkx).size() || chunky < 0) {
                    continue;
                }
                drawChunk(chunkx, chunky);
            }
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
