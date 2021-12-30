package com.volbot.lourts.Render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Combatant;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.Data.Battle;
import com.volbot.lourts.GUI.GameMenu;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.GUI.NotificationWindow;
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
        if(Main.GAMEMODE==0) {
            for (Agent a : Main.entities) {
                if (a instanceof Individual) {
                    if (a != Main.player)
                        batch.draw(texLoader.texUnits.get(a.theme).heroes.get(a.texID), cam.position.x + a.position.x * cam.zoom - size / 2, cam.position.y + a.position.y * cam.zoom - size / 2, size, size);
                }
                if (a instanceof Location) {
                    batch.draw(texLoader.texUnits.get(a.theme).towns.get(a.texID), a.position.x * cam.zoom - size + cam.position.x, a.position.y * cam.zoom - size + cam.position.y, size * 2, size * 2);
                }
            }
            if (Main.player.location == null) {
                batch.draw(texLoader.texUnits.get(Main.player.theme).heroes.get(Main.player.texID), Main.player.position.x * cam.zoom - size / 2 + cam.position.x, Main.player.position.y * cam.zoom - size / 2 + cam.position.y, size, size);
            }
        } else {
            Battle battle = Main.battle;
            for(Combatant c : battle.combatants){
                Texture tex;
                if(c.getAgent().getName().equals(battle.aggressor.getName()) || c.getAgent().getName().equals(battle.defender.getName())){
                    tex=texLoader.texUnits.get(c.theme).heroes.get(c.texID);
                } else {
                    tex=texLoader.texUnits.get(c.theme).combatants.get(c.texID);
                }
                batch.draw(tex,cam.position.x + c.position.x * cam.zoom - size / 2, cam.position.y + c.position.y * cam.zoom - size / 2, size, size);
            }
        }
        GameMenu tempMenu = Main.gui.currmenu;
        Agent hovered = Main.inputs.entityHovered(Main.inputs.getTouchPos());
        if (tempMenu != null) {
            if (tempMenu instanceof NotificationWindow) {
                NotificationWindow menu = (NotificationWindow) tempMenu;
                menu.drawMenu(batch,cam);
            }
            if (tempMenu instanceof InteractMenu) {
                InteractMenu menu = (InteractMenu) tempMenu;
                drawName(menu.getAgent());
                menu.drawMenu(batch, cam);
                if (hovered != null && hovered != menu.getAgent()) {
                    drawName(hovered);
                    if(Main.GAMEMODE==0) drawPopulation(hovered);
                }
            } else if (tempMenu instanceof TalkWindow) {
                TalkWindow menu = (TalkWindow) tempMenu;
                if (menu.conversation != null) {
                    menu.drawMenu(batch, cam);
                } else {
                    Main.gui.clearMenu();
                }
            }
        } else {
            if (hovered != null) {
                drawName(hovered);
                if(Main.GAMEMODE==0) drawPopulation(hovered);
            }
        }
        cam.update();
        batch.end();
    }

    public void drawName(Agent a) {
        Vector3 drawPos = a.position.cpy();
        font.getData().setScale(((cam.zoom-1)/2)+1);
        GlyphLayout layout = new GlyphLayout(font, a.getName());
        int tempHeight = a instanceof Location ? 40 : 30;
        font.draw(batch, layout, cam.position.x + drawPos.x*cam.zoom - layout.width / 2, cam.position.y + drawPos.y*cam.zoom + tempHeight*cam.zoom);
    }

    public void drawPopulation(Agent a) {
        font.getData().setScale(cam.zoom);
        GlyphLayout layout = new GlyphLayout(font, ""+a.getPopulation());
        int tempHeight = a instanceof Location ? 25 : 15;
        font.draw(batch, layout, cam.position.x + a.position.x*cam.zoom - layout.width / 2, cam.position.y + a.position.y*cam.zoom - tempHeight*cam.zoom);
    }

    private void drawChunk(int chunkx, int chunky) {
        float size = (20)*(cam.zoom);
        float posx = (chunkx * (size*24)) + cam.position.x;
        float posy;
        for (int drawx = 0; drawx < 24; drawx++) {
            posy = (chunky * (size*24)) + cam.position.y;
            for (int drawy = 0; drawy < 24; drawy++) {
                if ((chunkx >= Main.map.chunks.size() || chunkx < 0) || (chunky >= Main.map.chunks.get(chunkx).size() || chunky < 0)) {
                    batch.draw(texLoader.tiles.get(1),drawx * size + posx, drawy * size + posy,size,size);
                } else {
                    batch.draw(
                            texLoader.tiles.get(
                                    Main.map.chunks
                                            .get(chunkx)
                                            .get(chunky)
                                            [drawx][drawy]),
                            drawx * size + posx, drawy * size + posy, size, size
                    );
                }
            }
        }
    }

    private void drawMap() {
        float size = (20)*(cam.zoom);
        Vector3 camPosAbs = cam.position.cpy();
        camPosAbs.scl(-1f);
        float camxstart = camPosAbs.x;
        float camystart =camPosAbs.y;
        float camxend = camPosAbs.x + cam.viewportWidth;
        float camyend = camPosAbs.y + cam.viewportHeight;
        ArrayList<Integer> chunksx = new ArrayList<>();
        ArrayList<Integer> chunksy = new ArrayList<>();
        chunksx.add((int) Math.floor(camxstart / (24*size))-1);
        chunksx.add((int) Math.ceil(camxend / (24*size))+1);
        int i = chunksx.get(0);
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
            for (int chunky : chunksy) {
                drawChunk(chunkx, chunky);
            }
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
