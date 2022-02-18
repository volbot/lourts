package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.*;
import com.volbot.lourts.GUI.GUIManager;
import com.volbot.lourts.Data.Battle;
import com.volbot.lourts.GUI.MainMenu;
import com.volbot.lourts.Input.InputManager;
import com.volbot.lourts.Map.BattleMap;
import com.volbot.lourts.Map.GameMap;
import com.volbot.lourts.Render.Display;
import com.volbot.lourts.Render.TexLoader;
import com.volbot.lourts.Saves.SaveManager;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {

    public static OrthographicCamera cam;
    public static InputManager inputs;
    public static GUIManager gui;
    public static Display display;

    public static Random random = new Random();

    public static TexLoader texLoader;
    public static SaveManager saveMan = new SaveManager();

    public static Battle battle;
    public static GameMap map;

    public static ArrayList<Individual> entities = new ArrayList<>();
    public static ArrayList<Location> locations = new ArrayList<>();
    public static GameMap worldmap;

    public static Individual player;
    public static int GAMETIME;
    public static int GAMEMODE;
    public static boolean PAUSED = false;

    @Override
    public void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1024, 576);
        cam.position.set(-100, -100, 0);
        display = new Display(cam);
        GAMETIME = 0;
        GAMEMODE = -1;
        battle = null;
        texLoader = new TexLoader();
        gui = new GUIManager();
        inputs = new InputManager(cam);
        Gdx.input.setInputProcessor(inputs);
        worldmap = new GameMap();
    }

    public static Location findLoc(String name) {
        for(Location l : locations){
            if(l.getName().equals(name)){
                return l;
            }
        }
        return null;
    }

    public static Individual findHero(String name) {
        for(Individual i : entities){
            if(i.getName().equals(name)){
                return i;
            }
        }
        return null;
    }

    public static void initDemo() {
        Individual leftusgroblin = new Individual("Leftus Groblin");
        player = leftusgroblin;
        entities.add(leftusgroblin);
        leftusgroblin.position.x = 380;
        leftusgroblin.position.y = 380;
        leftusgroblin.texID = 2;
        Location boneland = new Location("Boneland", "Skeletrex", 200, 200, 100);
        Faction bonebrigade = new Faction("Bone Brigade", boneland.getFigurehead(), 0);
        locations.add(boneland);
        boneland.setFaction(bonebrigade);
        boneland.texID = 0;
        boneland.getFigurehead().texID = 4;
        Location bonetown = new Location("Bonetown", "Selgar", 750, 440, 233);
        bonetown.setFaction(bonebrigade);
        locations.add(bonetown);
        bonetown.texID = 0;
        bonetown.getFigurehead().texID = 3;
    }

    @Override
    public void render() {
        if (GAMEMODE == -1) {
            if (!(gui.currmenu instanceof MainMenu)) {
                gui.currmenu = new MainMenu();
            }
            display.loop();
            gui.loop();
            return;
        }
        if (GAMEMODE == 0) {
            GAMETIME++;
            if (map != worldmap) {
                map = worldmap;
            }
        }
        if (GAMEMODE == 1 && map == worldmap) {
            map = new BattleMap();
        }
        if (GAMETIME % 500 == 0) {
            saveMan.saveGame();
        }
        massThink();
        ScreenUtils.clear(1, 0, 0, 1);
        gui.loop();
        inputs.parseCameraMovement();
        if (!texLoader.texUnits.keySet().isEmpty()) {
            display.loop();
        } else {
            System.out.println("Textures could not be loaded!");
        }
    }

    public void massThink() {
        if (GAMEMODE == 0) {
            int len1 = entities.size();
            Agent entity;
            for (int j = 0; j < len1; j++) {
                entity = entities.get(j);
                entity.think();
                if (entity.equals(player) && inputs.camLockedToPlayer) {
                    cam.position.set(player.position.cpy().scl(-1 / cam.zoom));
                }
            }
            len1 = locations.size();
            Location loc;
            for (int j = 0; j < len1; j++) {
                loc = locations.get(j);
                ArrayList<Individual> heroes = loc.heroes;
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
        } else if (GAMEMODE == 1) {
            if (gui.currmenu == null) {
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
                    battleOver((Individual) battle.defender);
                } else if (dCount <= 0) {
                    battleOver((Individual) battle.aggressor);
                }
            }
        }
    }

    @Override
    public void dispose() {
        saveMan.saveGame();
        texLoader.dispose();
        display.dispose();
    }

    public static void battleOver(Individual victor) {
        if (victor == Main.player) Main.gui.drawNotificationWindow("battlewon");
        else if (Main.player.equals(
                Main.battle.aggressor.equals(victor) ?
                        Main.battle.defender :
                        Main.battle.aggressor)) {
            Main.gui.drawNotificationWindow("battlelost");
        } else {
            endBattle(victor);
        }
    }

    public static void endBattle(Agent victorIn) {
        Agent victor = victorIn;
        if (victor == null) {
            victor = battle.aggressor;
        }
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
        Agent loser = victor.equals(battle.aggressor) ? battle.defender : battle.aggressor;
        loser.getParty().sub(loserLost);
        GAMEMODE = 0;
        if (victor.equals(Main.player) || victorIn == null) {
            Main.gui.drawTalkMenu(loser, true);
        } else if (loser.equals(Main.player)) {
            Main.gui.drawTalkMenu(victor, false);
        }
        battle = null;
    }

    public static void setPaused(boolean val) {
        PAUSED = val;
    }
}
