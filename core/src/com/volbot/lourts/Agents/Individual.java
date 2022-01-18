package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Data.Stats;
import com.volbot.lourts.Main;
import com.volbot.lourts.Map.Tile;

public class Individual extends Agent {
    private final Stats stats; //INT, WIS, STR, DEX, CHA, PSI
    private int moveSpeed;
    public Vector3 goalPos;
    public Agent dest;
    public Location location;

    public Individual(String name) {
        super(name);
        theme = "base";
        stats = new Stats();
        moveSpeed = 70;
        goalPos = null;
        dest = null;
        location = null;
    }

    @Override
    public void think() {
        if (dest != null) {
            Vector3 destLoc = new Vector3(dest.position.x, dest.position.y, 0);
            if (position.dst(destLoc) < 30) {
                if (location == null && dest instanceof Location) {
                    Main.entities.remove(this);
                    ((Location) dest).heroes.add(this);
                    this.location = (Location) dest;
                }
                if (this.equals(Main.player)) {
                    dest = null;
                }
            }
            goalPos = destLoc;
        }
        if (goalPos != null) {
            if (!move(goalPos)) {
                goalPos = null;
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
        this.goalPos = null;
        this.dest = destination;
    }

    @Override
    public void setFaction(Faction faction) {
        super.setFaction(faction);
        faction.heroes.add(this);
    }

    @Override
    public void interact(Agent a) {

    }

    public void setDestination(Vector3 goalPos) {
        //if(!Main.map.chunks.getTile(goalPos.x,goalPos.y).walkable) return;
        this.dest = null;
        this.goalPos = goalPos;
    }

    public Agent getDestination() {
        return this.dest;
    }

    public boolean move(Vector3 goal) {
        if (Main.PAUSED) {
            return true;
        }
        if (this.location != null) {
            this.location.heroes.remove(this);
            this.location = null;
            Main.entities.add(this);
        }

        float dst = goal.cpy().dst(position);
        Vector3 movement;
        float workingSpeed = Gdx.graphics.getDeltaTime() * moveSpeed;

        Vector3 newPos = position.cpy();
        Tile[] tiles = new Tile[5];
        int[][] poss = new int[5][3];
        poss[0][0] = (int) position.x / 20;
        poss[0][1] = (int) position.y / 20;
        poss[1][0] = poss[0][0] + 1;
        poss[1][1] = poss[0][1];
        poss[2][0] = poss[0][0] - 1;
        poss[2][1] = poss[0][1];
        poss[3][0] = poss[0][0];
        poss[3][1] = poss[0][1] + 1;
        poss[4][0] = poss[0][0];
        poss[4][1] = poss[0][1] - 1;

        int bdist = 0;
        for (int i = 0; i < 5; i++) {
            tiles[i] = Main.map.chunks.getTile(poss[i][0], poss[i][1]);
            poss[i][2] = (tiles[i].walkable ? 0 : -1);
            if (poss[i][2] > -1) {
                if (goal.cpy().dst(new Vector3((poss[i][0] * 20), (poss[i][1] * 20), 0)) <
                        goal.cpy().dst(new Vector3((poss[bdist][0] * 20), (poss[bdist][1] * 20), 0))) {
                    if(bdist!=0) poss[bdist][2] -= 1;
                    bdist = i;
                    poss[i][2] += 1;
                }
            }
            System.out.println(poss[i][2]);
        }
        int bestPoss = 0;
        for (int i = 1; i < 5; i++) {
            if (poss[i][2] != -1 && poss[i][2] > poss[bestPoss][2]) {
                bestPoss = i;
            }
        }
        if (poss[bestPoss][2] < 0) {
            System.out.println("deez");
            for (int i = 0; i < 5; i++) {
                if (poss[i][2] > -1) {
                    position.set(poss[i][0] * 20 + 10, poss[i][1] * 20 + 10, 0);
                }
            }
        } else
        if (dst > 10) {
            movement = new Vector3((poss[bestPoss][0] * 20), (poss[bestPoss][1] * 20), 0).sub(new Vector3(poss[0][0] * 20, poss[0][1] * 20, 0)).setLength(workingSpeed);
            newPos.add(movement);
            if (goal.dst(newPos) < dst) {
                position.add(movement);
                return true;
            }
        }
        return false;
    }
}
