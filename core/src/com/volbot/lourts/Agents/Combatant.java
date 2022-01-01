package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Main;

public class Combatant {

    public Object entity;
    public Agent allegiance;

    public int texID;
    public String theme;

    public Vector3 position= new Vector3(0, 0, 0);;
    public int health = 40;
    public int attackDamage = 7;
    public int attackCooldown = 40;
    public int attackTime = attackCooldown;
    public int attackDistance = 30;
    private Combatant target = null;

    public Combatant(Agent a) {
        entity = a;
        texID = a.texID;
        theme = a.theme;
        allegiance = a;
    }

    public Combatant(Demographic d, Agent a) {
        Demographic d2 = new Demographic(d.getOrigin(), 1, d.getLevel());
        entity = d2;
        texID = d.texID;
        theme = d.theme;
        allegiance = a;
    }

    public Combatant setPosition(Vector3 position) {
        this.position = position;
        return this;
    }

    public Agent getAgent() {
        String name;
        if (entity instanceof Agent) {
            name = ((Agent) entity).getName();
        } else {
            Demographic d = (Demographic) entity;
            name = d.getName();
        }
        if(health<=0){
            return null;
        }
        Agent temp = new Agent(name);
        temp.position.set(position.cpy());
        return temp;
    }

    public void think() {
        if(health<=0) {
            return;
        }
        perceive();
        if (target != null) {
            if(position.dst(target.position)>attackDistance) {
                move(target.position);
            } else {
                if(attackTime==attackCooldown) {
                    attack(target);
                    attackTime=0;
                } else {
                    attackTime++;
                }
            }
        }
    }

    public void dealDamage(int damage){
        health=Math.max(health-damage,0);
    }


    private void attack(Combatant target){
        target.dealDamage(attackDamage);
    }

    private void perceive() {
        Combatant closestEnemy = null;
        float closestEnemyDist = 0.0f;
        for (Combatant c : Main.battle.combatants) {
            if(c.health<=0) continue;
            float dst = position.dst(c.position);
            if(!c.equals(this)){
                while((dst=c.position.dst(position))<20) {
                    c.position.add(c.position.cpy().sub(position).scl(0.05f));
                }
            }
            position=Main.inputs.boundClick(position);
            if (c.allegiance != this.allegiance) {
                if (closestEnemy == null || dst < closestEnemyDist) {
                    closestEnemy = c;
                    closestEnemyDist = dst;

                }
            }
        }
        if (target == null) target=closestEnemy;
        if (target != null && target.health<=0) target=null;
    }

    public boolean move(Vector3 goalIn) {
        if (Main.PAUSED) return true;
        Vector3 goal = goalIn.cpy();
        float dst = goal.cpy().dst(position);
        Vector3 movement;
        float workingSpeed = Gdx.graphics.getDeltaTime() * 70;
        Vector3 newPos = position.cpy();
        if (dst > attackDistance) {
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