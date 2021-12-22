package com.volbot.lourts.Agents;

import com.volbot.lourts.Data.Background;
import com.volbot.lourts.Data.Personality;
import com.volbot.lourts.GUI.GameWindow;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.Main;

public class Hero extends Individual {
    private final Personality personality;
    private final Background background;

    int viewDistance = 150;
    int intent = 0;

    public Hero(String name) {
        super(name);
        personality = new Personality(new float[]{0, 0, 0, 0, 0, 0}, new float[]{0, 0, 0, 0});
        background = new Background(new Location("Nowhere"), 0);
    }

    public Hero(String name, Location origin) {
        super(name);
        personality = origin.getPersonality();
        background = new Background(origin, 0);
        this.location=origin;
        this.position=origin.position.cpy();
        origin.heroes.add(this);
    }

    @Override
    public void think() {
        super.think();
        if(dest!=null) {
            if (position.dst(dest.position) < 20) {
                dest=null;
                if (intent == 1) {
                    Main.player.dest=null;
                    Main.player.goalPos=null;
                    if(!(Main.gui.currmenu instanceof GameWindow)) {
                        Main.gui.drawTalkMenu(this);
                    }
                    intent = 0;
                }
            }
        } else if(goalPos!=null) {
            if(position.dst(goalPos)<10){
                goalPos=null;
            }
        }
        decide();
    }

    protected void decide() {
        if(dest==null && goalPos==null){
            for(Agent a : Main.entities){
                if(a instanceof Location || a.equals(this)) continue;
                if(position.dst(a.position)<viewDistance){
                    if(!rep.knows(a)){
                        intent = 1;
                        setDestination(a);
                    }
                }
            }
            if (!(Main.gui.currmenu instanceof GameWindow)) {
                if(dest==null && goalPos==null){
                    dest=background.origin;
                }
            }
        }
    }

    public Personality getPersonality() {
        return personality;
    }

    public Background getBackground() {
        return background;
    }
}
