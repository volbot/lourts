package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Main;

import java.util.ArrayList;

public class Display {

    private final TexLoader texLoader;
    private final SpriteBatch batch;
    private final Camera cam;
    private final ArrayList<Agent> entities;

    public Display(Camera camera) {
        cam = camera;
        texLoader = new TexLoader();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        entities = new ArrayList<>();
    }

    public void loop() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        drawMap();
        for (Agent a : entities) {
            batch.draw(texLoader.heroes.get(0), cam.position.x + a.x - 10, cam.position.y + a.y - 10);
        }
        batch.end();
    }

    private void drawChunk(int chunkx, int chunky) {
        int posx = (chunkx*(int)cam.viewportWidth)-(int)cam.position.x;
        int posy;
        for (int drawx = 0; drawx < 24; drawx++) {
            posy = (chunky*(int)cam.viewportHeight)-(int)cam.position.y;
            for (int drawy = 0; drawy < 24; drawy++) {
                batch.draw(
                        texLoader.tiles.get(
                                Main.map.chunks
                                        .get(chunkx)
                                        .get(chunky)
                                        [drawx][drawy]),
                        drawx*20-posx, drawy*20-posy
                );
            }
        }
    }

    private void drawMap() {
        int camxstart = Math.round(cam.position.x);
        int camystart = Math.round(cam.position.y);
        int camxend = Math.round(camxstart + cam.viewportWidth);
        int camyend = Math.round(camystart + cam.viewportHeight);
        ArrayList<Integer> chunksx = new ArrayList<>();
        ArrayList<Integer> chunksy = new ArrayList<>();
        int counter = camxstart;
        while (counter <= camxend) {
            if (!chunksx.contains(counter / 480)) {
                chunksx.add(counter / 480);
            }
            counter += 20;
        }
        counter = camystart;
        while (counter <= camyend) {
            if (!chunksy.contains(counter / 480)) {
                chunksy.add(counter / 480);
            }
            counter += 20;
        }
        for (int chunkx : chunksx) {
            if (chunkx >= Main.map.chunks.size()) {
                continue;
            }
            for (int chunky : chunksy) {
                if (chunky >= Main.map.chunks.get(chunkx).size()) {
                    continue;
                }
                drawChunk(chunkx,chunky);
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
