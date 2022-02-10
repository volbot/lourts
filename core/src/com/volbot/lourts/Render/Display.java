package com.volbot.lourts.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.volbot.lourts.GUI.*;
import com.volbot.lourts.Main;
import com.volbot.lourts.Map.GameMap;

import java.util.ArrayList;

public class Display {

    private final SpriteBatch batch;
    private final OrthographicCamera cam;

    private final BitmapFont font;

    public Display(OrthographicCamera camera) {
        cam = camera;
        batch = new SpriteBatch();
        font = new BitmapFont();
        batch.setProjectionMatrix(cam.combined);

    }

    public void loop() {
        batch.begin();
        float size = (20) * (cam.zoom);
        ScreenUtils.clear(0, 0, 0, 1);
        if(Main.GAMEMODE == -1) {
            Main.gui.currmenu.drawMenu(batch,cam);
            batch.end();
            return;
        }
        Main.map.chunks.drawNode(batch, cam);
        if (Main.GAMEMODE == 0) {
            for (Agent a : Main.entities) {
                Texture tex;
                if (a instanceof Individual) {
                    if (a != Main.player) {
                        tex = Main.texLoader.texUnits.get(a.theme).heroes.get(a.texID);
                        if (tex != null)
                            batch.draw(tex, cam.position.x + a.position.x * cam.zoom - size / 2, cam.position.y + a.position.y * cam.zoom - size / 2, size, size);
                    }
                }
                if (a instanceof Location) {
                    tex = Main.texLoader.texUnits.get(a.theme).towns.get(a.texID);
                    if (tex != null)
                        batch.draw(tex, a.position.x * cam.zoom - size + cam.position.x, a.position.y * cam.zoom - size + cam.position.y, size * 2, size * 2);
                }
            }
            if (Main.player.location == null) {
                Texture tex = Main.texLoader.texUnits.get(Main.player.theme).heroes.get(Main.player.texID);
                if (tex != null) batch.draw(tex, Main.player.position.x * cam.zoom - size / 2 + cam.position.x, Main.player.position.y * cam.zoom - size / 2 + cam.position.y, size, size);
            }
        } else {
            Battle battle = Main.battle;
            for (Combatant c : battle.combatants) {
                if (c.health <= 0) continue;
                Texture tex;
                if (c.getAgent().getName().equals(battle.aggressor.getName()) || c.getAgent().getName().equals(battle.defender.getName())) {
                    tex = Main.texLoader.texUnits.get(c.theme).heroes.get(c.texID);
                } else {
                    tex = Main.texLoader.texUnits.get(c.theme).combatants.get(c.texID);
                }
                batch.draw(tex, cam.position.x + c.position.x * cam.zoom - size / 2, cam.position.y + c.position.y * cam.zoom - size / 2, size, size);
            }
        }
        GameMenu tempMenu = Main.gui.currmenu;
        Agent hovered = Main.inputs.entityHovered(Main.inputs.getTouchPos());
        if (tempMenu != null) {
            if (tempMenu instanceof NotificationWindow) {
                NotificationWindow menu = (NotificationWindow) tempMenu;
                menu.drawMenu(batch, cam);
            }
            if (tempMenu instanceof InteractMenu) {
                InteractMenu menu = (InteractMenu) tempMenu;
                drawName(menu.getAgent());
                menu.drawMenu(batch, cam);
                if (hovered != null && hovered != menu.getAgent()) {
                    drawName(hovered);
                    if (Main.GAMEMODE == 0) drawPopulation(hovered);
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
            Main.gui.hud.drawMenu(batch,cam);
            if (hovered != null) {
                drawName(hovered);
                if (Main.GAMEMODE == 0) drawPopulation(hovered);
            }
        }
        cam.update();
        batch.end();
    }

    public void drawName(Agent a) {
        Vector3 drawPos = a.position.cpy();
        font.getData().setScale(((cam.zoom - 1) / 2) + 1);
        GlyphLayout layout = new GlyphLayout(font, a.getName());
        int tempHeight = a instanceof Location ? 40 : 30;
        font.draw(batch, layout, cam.position.x + drawPos.x * cam.zoom - layout.width / 2, cam.position.y + drawPos.y * cam.zoom + tempHeight * cam.zoom);
    }

    public void drawPopulation(Agent a) {
        font.getData().setScale(cam.zoom);
        GlyphLayout layout = new GlyphLayout(font, "" + a.getPopulationSize());
        int tempHeight = a instanceof Location ? 25 : 15;
        font.draw(batch, layout, cam.position.x + a.position.x * cam.zoom - layout.width / 2, cam.position.y + a.position.y * cam.zoom - tempHeight * cam.zoom);
    }

    private void drawMap() {
        float size = (20) * (cam.zoom);
        Vector3 camPosAbs = cam.position.cpy();
        camPosAbs.scl(-1f);
        float camxstart = camPosAbs.x;
        float camystart = camPosAbs.y;

        for (float x = camxstart; x < cam.viewportWidth + camxstart; x += size) {
            for (float y = camystart; y < cam.viewportHeight + camystart; y += size) {

            }
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
