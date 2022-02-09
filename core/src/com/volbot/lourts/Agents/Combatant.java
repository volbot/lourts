package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Main;
import com.volbot.lourts.Map.Tile;

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
        entity = new Demographic(d.getOrigin(), 1, d.getLevel());
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

    public boolean move(Vector3 goal) {
        if (Main.PAUSED) {
            return true;
        }

        float dst = goal.cpy().dst(position);
        Vector3 movement;
        float workingSpeed = Gdx.graphics.getDeltaTime() * 70;

        Vector3 newPos = position.cpy();
        if (dst > 10) {
            movement = goal.cpy().sub(position).setLength(workingSpeed);
            newPos.add(movement);
        }
        if (position.dst(newPos) != 0 && Main.map.chunks.getTile((int)(newPos.x/20),(int)(newPos.y/20)).walkable) {
            position = newPos.cpy();
            return true;
        }

        Tile[] tiles = new Tile[5];
        int[][] poss = new int[5][3];
        poss[0][0] = (int)Math.floor(position.x / 20);
        poss[0][1] = (int)Math.floor(position.y / 20);
        poss[1][0] = poss[0][0] + 1;
        poss[1][1] = poss[0][1];
        poss[2][0] = poss[0][0] - 1;
        poss[2][1] = poss[0][1];
        poss[3][0] = poss[0][0];
        poss[3][1] = poss[0][1] + 1;
        poss[4][0] = poss[0][0];
        poss[4][1] = poss[0][1] - 1;

        int bestPoss = 0;
        for (int i = 0; i < 5; i++) {
            tiles[i] = Main.map.chunks.getTile(poss[i][0], poss[i][1]);
            poss[i][2] = (tiles[i].walkable ? 0 : -1);
            if(poss[i][2]>-1) {
                float dstTemp = new Vector3(poss[i][0]*20,poss[i][1]*20,0).dst(goal);
                poss[i][2] += (dstTemp < dst ? 1 : 0);
                poss[i][2] += (dst - dstTemp > 10 ? 1 : 0);
                poss[i][2] += (dst - dstTemp > 30 ? 1 : 0);
            }
            if (i > 0) {
                if (poss[i][2] != -1 && poss[i][2] > poss[bestPoss][2]) {
                    if(new Vector3(20*poss[bestPoss][0],20*poss[bestPoss][1],0).dst(goal) > new Vector3(20*poss[i][0],20*poss[i][1],0).dst(goal)) bestPoss = i;
                } else if (bestPoss == 0) {
                    if (poss[i][2] > -1) {
                        bestPoss = i;
                    }
                }
            }
        }

        if (bestPoss == 0) {
            for (int i = 0; i < 5; i++) {
                if (poss[i][2] > -1) {
                    position.set(poss[i][0] * 20 + 10, poss[i][1] * 20 + 10, 0);
                    break;
                }
            }
        } else if (dst > 10) {
            movement = new Vector3((poss[bestPoss][0] * 20), (poss[bestPoss][1] * 20), 0).sub((poss[0][0] * 20), (poss[0][1] * 20), 0).setLength(workingSpeed);
            position.add(movement);
            return true;
        }
        return false;
    }
}