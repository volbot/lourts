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
        personality = origin.getPersonality();
        background = new Background(origin, 0);
        this.location=origin;
        this.position=origin.position.cpy();
        origin.heroes.add(this);
    }

    @Override
    public void interact(Agent a) {
        super.interact(a);
        if(dest.equals(Main.player)) {
            if (!(Main.gui.currmenu instanceof GameWindow)) {
                Main.gui.drawTalkMenu(this);
            }
        }
    }

    @Override
    public void think() {
        super.think();
        if(dest!=null) {
            if (position.dst(dest.position) < 20) {
                if (intent == 1) {
                    this.interact(dest);

                    intent = 2;
                }
                dest=null;
            }
        } else if(goalPos!=null) {
            if(position.dst(goalPos)<10){
                goalPos=null;
            }
        }
        decide();
    }

    protected void decide() {
        decisionTime++;
        if (intent == 0 || intent == 2) {
            intent = 2;
                for(Agent a : Main.entities){
                    if(a instanceof Location || a.equals(this)) continue;
                    if(position.dst(a.position)<viewDistance){
                        if(!rep.knows(a)){
                            decisionTime = 0;
                            intent = 1;
                            setDestination(a);
                        }
                    }
                }
                if (!(Main.gui.currmenu instanceof GameWindow) && intent!=2) {
                    if(dest==null && goalPos==null){
                        dest=background.origin;
                    }
                }
        }
        if(intent == 2) {
            if(decisionTime>500) {
                decisionTime=0;
                setDestination(patrol(background.origin));
            }
        }
    }

    private Vector3 patrol(Location loc) {
        Vector3 pos = loc.position.cpy();
        Random rand = new Random();
        pos.x+=rand.nextInt(200)+(-100);
        pos.y+=rand.nextInt(200)+(-100);
        return pos;
    }

    public Personality getPersonality() {
        return personality;
    }

    public Background getBackground() {
        return background;
    }
}