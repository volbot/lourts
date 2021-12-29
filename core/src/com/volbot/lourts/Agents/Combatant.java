package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Main;

public class Combatant {

    public Object entity;
    public int texID;
    public String theme;
    public Vector3 position;
    public Agent allegiance;
    private Combatant target = null;

    public Combatant(Agent a) {
        entity = a;
        texID = a.texID;
        theme = a.theme;
        allegiance = a;
        position = new Vector3(0,0,0);
    }
    public Combatant(Demographic d, Agent a) {
        entity = d;
        texID = d.texID;
        theme = d.theme;
        allegiance=a;
        position = new Vector3(0,0,0);
    }

    public Combatant setPosition(Vector3 position) {
        this.position = position;
        return this;
    }

    public Agent getAgent() {
        if (entity instanceof Agent) {
            return (Agent) entity;
        } else {
            Demographic d = (Demographic) entity;
            return new Agent(d.getName());
        }
    }

    public void think() {
        if(target==null){
            target=closestEnemy();
        }
        if(target!=null){
            move(target.position);
        }
    }

    private Combatant closestEnemy() {
        Combatant closestEnemy = null;
        float closestDist = 0.0f;
        for(Combatant c : Main.battle.combatants){
            if(c.allegiance==this.allegiance) continue;
            float dst = position.dst(c.position);
            if(closestEnemy==null || dst<closestDist){
                closestEnemy=c;
                closestDist=dst;
            }
        }
        return closestEnemy;
    }

    public boolean move(Vector3 goal) {
        if(Main.PAUSED){
            return true;
        }
        double xdist = goal.x - position.x;
        double ydist = goal.y - position.y;
        double xtravel = 0;
        double ytravel = 0;
        double workingSpeed = Gdx.graphics.getDeltaTime() * 70;

        if (Math.abs(xdist) > 2*workingSpeed || Math.abs(ydist) > 2*workingSpeed) {
            //if far from goal, go to it
            xtravel = workingSpeed * (Math.abs(xdist) / (Math.abs(xdist) + Math.abs(ydist)));
            ytravel = workingSpeed - xtravel;
            xtravel = Math.ceil(xtravel);
            ytravel = Math.ceil(ytravel);
        }

        //set directions
        if (xdist < 0 && xtravel > 0) {
            xtravel = -xtravel;
        }
        if (ydist < 0 && ytravel > 0) {
            ytravel = -ytravel;
        }

        boolean returnval = false;
        if (Math.abs(xdist) > 10) {
            position.x += xtravel;
            returnval = true;
        }
        if (Math.abs(ydist) > 10) {
            position.y += ytravel;
            returnval = true;
        }
        return returnval;
    }
}