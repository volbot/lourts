package com.volbot.lourts.Agents;

import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Data.Background;
import com.volbot.lourts.Data.Personality;
import com.volbot.lourts.GUI.GameWindow;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.Main;

import java.util.Random;

public class Hero extends Individual {
    private final Personality personality;
    private final Background background;

    private int decisionTime;

    int viewDistance = 150;
    int intent = 0;

    public Hero(String name, Location origin) {
        super(name);
        decisionTime=Main.GAMETIME;
        personality = origin.getPersonality();
        background = new Background(origin, 0);
        this.location = origin;
        this.position = origin.position.cpy();
        origin.heroes.add(this);
    }

    @Override
    public void interact(Agent a) {
        super.interact(a);
        if (dest==Main.player) {
            if (!(Main.gui.currmenu instanceof GameWindow)) {
                if (intent == 1 || intent == 2) Main.gui.drawTalkMenu(this);
                if (intent == 3) Main.gui.drawCombatMenu(this);
            }
        }
    }

    @Override
    public void think() {
        super.think();
        if (dest != null) {
            if (position.dst(dest.position) < 30) {
                interact(dest);
                dest = null;
            }
        } else if (goalPos != null) {
            if (position.dst(goalPos) < 10) {
                goalPos = null;
            }
        }
        decide();
    }

    protected void decide() {
        switch (intent) {
            case 0: //default
                intent = 2; //switch to patrol
                break;
            case 1: //talk to agent
                break;
            case 2: //patrol
                if (Main.GAMETIME-decisionTime > 200 && Main.random.nextInt(6)>=5) {
                        decisionTime = Main.GAMETIME;
                        setDestination(patrol(background.origin));
                }
                for (Agent a : Main.entities) {
                    if (a instanceof Location || a.equals(this)) continue;
                    if (position.dst(a.position) < viewDistance && Main.random.nextInt(6)>=1) {
                        if (!rep.knows(a)) {
                            decisionTime = Main.GAMETIME;
                            setDestination(a);
                        }
                    }
                }
                break;
            case 3: //battle agent
                break;
        }
    }

    @Override
    public void setFaction(Faction faction) {
        super.setFaction(faction);
    }

    private Vector3 patrol(Location loc) {
        Vector3 pos = loc.position.cpy();
        Random rand = new Random();
        pos.x += rand.nextInt(200) + (-100);
        pos.y += rand.nextInt(200) + (-100);
        return pos;
    }

    public Personality getPersonality() {
        return personality;
    }

    public Background getBackground() {
        return background;
    }
}