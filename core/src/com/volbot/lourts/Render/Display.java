package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.GUI.GameMenu;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.Main;

import java.util.ArrayList;

public class Display {

    private final TexLoader texLoader;
    private final SpriteBatch batch;
    private final Camera cam;
    private final ArrayList<Agent> entities;

    private final BitmapFont font;

    public Display(Camera camera) {
        cam = camera;
        texLoader = new TexLoader();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        entities = new ArrayList<>();
        font = new BitmapFont();
    }

    public void loop() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        drawMap();
        int i = 0;
        for (Agent a : entities) {
            if (a instanceof Individual) {
                batch.draw(texLoader.heroes.get(i), cam.position.x + a.x - 10, cam.position.y + a.y - 10);
                i++;
            }
            if (a instanceof Location) {
                batch.draw(texLoader.heroes.get(0), cam.position.x + a.x - 10, cam.position.y + a.y - 10);
            }
        }
        GameMenu tempMenu = Main.gui.currmenu;
        Agent hovered = Main.inputs.entityHovered();
        if (tempMenu != null) {
            if (tempMenu instanceof InteractMenu) {
                InteractMenu menu = (InteractMenu) tempMenu;
                drawName(menu.getAgent());
                menu.drawMenu(batch, cam);
                if (hovered != null && hovered != menu.getAgent()) {
                    drawName(hovered);
                }
            }
        } else {
            if (hovered != null) {
                drawName(hovered);
            }
        }
        batch.end();
    }


    public void drawName(Agent a) {
        GlyphLayout layout = new GlyphLayout(font, a.getName());
        font.draw(batch, layout, cam.position.x + a.x - layout.width / 2, cam.position.y + a.y + 30);
    }

    private void drawChunk(int chunkx, int chunky) {
        int posx = (chunkx * 480) - (int) cam.position.x;
        int posy;
        for (int drawx = 0; drawx < 24; drawx++) {
            posy = (chunky * 480) - (int) cam.position.y;
            for (int drawy = 0; drawy < 24; drawy++) {
                batch.draw(
                        texLoader.tiles.get(
                                Main.map.chunks
                                        .get(chunkx)
                                        .get(chunky)
                                        [drawx][drawy]),
                        drawx * 20 - posx, drawy * 20 - posy
                );
            }
        }
    }

    private void drawMap() {
        int camxstart = (int) Math.floor(cam.position.x - cam.viewportWidth);
        int camystart = (int) Math.floor(cam.position.y - cam.viewportHeight);
        int camxend = (int) Math.ceil(cam.position.x + cam.viewportWidth);
        int camyend = (int) Math.ceil(cam.position.y + cam.viewportHeight);
        ArrayList<Integer> chunksx = new ArrayList<>();
        ArrayList<Integer> chunksy = new ArrayList<>();
        chunksx.add((int) Math.ceil(camxstart / 480));
        chunksx.add((int) Math.ceil(camxend / 480));
        int i = chunksx.get(0) + 1;
        while (i < chunksx.get(1)) {
            chunksx.add(i);
            i++;
        }
        chunksy.add((int) Math.ceil(camystart / 480));
        chunksy.add((int) Math.ceil(camyend / 480));
        i = chunksy.get(0) + 1;
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

    public void displayEntity(Agent a) {
        entities.add(a);
    }

    public void hideEntity(Agent a) {
        entities.remove(a);
    }

    public void dispose() {
        batch.dispose();
    }
}
