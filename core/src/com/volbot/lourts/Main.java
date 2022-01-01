package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.*;
import com.volbot.lourts.Data.Battle;
import com.volbot.lourts.GUI.GUIManager;
import com.volbot.lourts.Input.InputManager;
import com.volbot.lourts.Map.BattleMap;
import com.volbot.lourts.Map.GameMap;
import com.volbot.lourts.Render.Display;
import com.volbot.lourts.Render.TexLoader;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {

    private OrthographicCamera cam;

    public static GUIManager gui;
    public static Display display;
    public static Battle battle;
    public static ArrayList<Agent> entities = new ArrayList<>();
    public static GameMap worldmap;
    public static GameMap map;
    public static TexLoader texLoader;
    public static InputManager inputs;
    public static Random random = new Random();

    public static Individual player;
    public static int GAMETIME;
    public static int GAMEMODE;
    public static boolean PAUSED = false;

    @Override
    public void create() {
        GAMETIME = 0;
        GAMEMODE = 0;
        battle = null;
        worldmap = new GameMap();
        map = worldmap;
        texLoader = new TexLoader();
        Individual crabwizard = new Individual("Crabwizard");
        player = crabwizard;
        entities.add(crabwizard);
        gui = new GUIManager();
        crabwizard.position.x = 400;
        crabwizard.position.y = 400;
        crabwizard.texID = 1;

        Location boneland = new Location("Boneland", "Skeletrex", 200, 200, 100);
        Faction bonebrigade = new Faction("Bone Brigade", boneland.getFigurehead(), 0);
        entities.add(boneland);
        boneland.setFaction(bonebrigade);
        boneland.texID = 0;
        Location bonetown = new Location("Bonetown", "Anthony Hopkins", 800, 440, 233);
        bonetown.setFaction(bonebrigade);
        entities.add(bonetown);
        bonetown.texID = 0;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1024, 576);
        cam.position.x = 0;
        cam.position.y = 0;
        display = new Display(cam);
        inputs = new InputManager(cam);
        Gdx.input.setInputProcessor(inputs);
    }

    @Override
    public void render() {
        if (GAMEMODE == 0) {
            GAMETIME++;
            if (map != worldmap) {
                map = worldmap;
                initWorld();
            }
        }
        if (GAMEMODE == 1 && map == worldmap) {
            map = new BattleMap();
        }
        massThink();
        ScreenUtils.clear(1, 0, 0, 1);
        if (gui.currmenu != null) {
            gui.loop();
        }
        inputs.parseCameraMovement();
        display.loop();
    }

    public void massThink() {
        if (GAMEMODE == 0) {
            int len1 = entities.size();
            Agent entity;
            for (int j = 0; j < len1; j++) {
                entity = entities.get(j);
                if (entity instanceof Individual) {
                    entity.think();
                } else if (entity instanceof Location) {
                    ArrayList<Individual> heroes = ((Location) entity).heroes;
                    if (!heroes.isEmpty()) {
                        int len2 = heroes.size();
                        for (int i = 0; i < len2; i++) {
                            heroes.get(i).think();
                            if (heroes.size() < len2) {
                                len2--;
                                i--;
                            }
                        }
                    }
                }
                if (entities.size() < len1) {
                    len1--;
                    j--;
                }
                if (entities.size() > len1) {
                    len1++;
                }
            }
        } else if (GAMEMODE == 1) {
            int len1 = battle.combatants.size();
            int aCount = 0;
            int dCount = 0;
            for (int i = 0; i < len1; i++) {
                Combatant c = battle.combatants.get(i);
                c.think();
                if (c.health > 0) {
                    if (c.allegiance.getName().equals(battle.aggressor.getName())) aCount++;
                    else if (c.allegiance.getName().equals(battle.defender.getName())) dCount++;
                }
            }
            if (aCount <= 0) {
                endBattle((Individual) battle.defender);
            } else if (dCount <= 0) {
                endBattle((Individual) battle.aggressor);
            }
        }
    }

    @Override
    public void dispose() {
        display.dispose();
        texLoader.dispose();
    }

    public static void endBattle(Individual victor) {
        ArrayList<Demographic> victorLost = new ArrayList<>();
        ArrayList<Demographic> loserLost = new ArrayList<>();
        for (Combatant c : battle.combatants) {
            if (c.entity instanceof Demographic) {
                if (c.health <= 0) {
                    Demographic d = (Demographic) c.entity;
                    boolean found = false;
                    if (c.allegiance.equals(victor)) {
                        for (Demographic d2 : victorLost) {
                            if (d.getName().equals(d2.getName())) {
                                found = true;
                                d2.setPopulation(d2.getPopulation() + 1);
                            }
                        }
                        if (!found) {
                            victorLost.add(d);
                        }
                    } else {
                        for (Demographic d2 : loserLost) {
                            if (d.getName().equals(d2.getName())) {
                                found = true;
                                d2.setPopulation(d2.getPopulation() + 1);
                            }
                            if (!found) {
                                loserLost.add(d);
                            }
                        }
                        if (!found) {
                            loserLost.add(d);
                        }
                    }
                }
            }
        }
        victor.getParty().sub(victorLost);
        Individual loser = victor.equals(battle.aggressor) ? (Individual) battle.defender : (Individual) battle.aggressor;
        loser.getParty().sub(loserLost);
        GAMEMODE = 0;
        if (victor.equals(Main.player)) {
            Main.gui.drawTalkMenu(loser, false);
        } else if (loser.equals(Main.player)) {
            Main.gui.drawTalkMenu(victor, true);
        }
        battle = null;
    }

    public void initWorld() {

    }

    public static void setPaused(boolean val) {
        PAUSED = val;
    }
}
