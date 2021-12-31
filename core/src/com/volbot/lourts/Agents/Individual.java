package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Data.Stats;
import com.volbot.lourts.GUI.GUIManager;
import com.volbot.lourts.GUI.GameMenu;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.Main;

import java.util.ArrayList;

public class Individual extends Agent{
    private final Stats stats; //INT, WIS, STR, DEX, CHA, PSI
    private int moveSpeed;
    public Vector3 goalPos;
    public Agent dest;
    public Location location;
    private final Population population;

    public Individual(String name){
        super(name);
        theme="base";
        stats = new Stats();
        moveSpeed = 70;
        goalPos=null;
        dest=null;
        location=null;
        population = new Population();
    }

    @Override
    public void think() {
        if(dest!=null){
            Vector3 destLoc = new Vector3(dest.position.x,dest.position.y,0);
            move(destLoc);
            if(position.dst(dest.position)<10 || dest.equals(this.location)){
                if(this.equals(Main.player)) Main.gui.playerOpenWindow();
                if(location==null && dest instanceof Location){
                    Main.entities.remove(this);
                    ((Location) dest).heroes.add(this);
                    this.location=(Location)dest;
                }
                dest=null;
            }
        }
        if (goalPos != null) {
            if(!move(goalPos)){
                goalPos=null;
            }
        }
    }


    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public Vector3 getGoalPos() {
        return goalPos;
    }

    public void setDestination(Agent destination) {
        this.goalPos=null;
        this.dest = destination;
    }

    @Override
    public void setFaction(Faction faction) {
        super.setFaction(faction);
        faction.heroes.add(this);
    }

    @Override
    public int getPopulation() {
        int popu = 1;
        for(Demographic demo : population.pop) popu+=demo.getPopulation();
        return popu;
    }

    public Population getParty() {
        return population;
    }
    @Override
    public void interact(Agent a) {

    }

    public void setDestination(Vector3 goalPos) {
        this.dest = null;
        this.goalPos = goalPos;
    }
    public Agent getDestination(){
        return this.dest;
    }

    public boolean move(Vector3 goal) {
        if (Main.PAUSED) {
            return true;
        }
        if(this.location!=null){
            this.location.heroes.remove(this);
            this.location=null;
            Main.entities.add(this);
        }
        float dst = goal.cpy().dst(position);
        Vector3 movement;
        float workingSpeed = Gdx.graphics.getDeltaTime() * moveSpeed;

        Vector3 newPos = position.cpy();
        if (dst > 10) {
            movement = goal.cpy().sub(position).setLength(workingSpeed);
            newPos.add(movement);
        }
        if (position.dst(newPos) != 0) {
            position = newPos.cpy();
            return true;
        }
        return false;
    }
}
