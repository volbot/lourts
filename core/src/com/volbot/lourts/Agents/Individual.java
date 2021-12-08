package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.volbot.lourts.Data.Stats;

public class Individual extends Agent{
    private final Stats stats; //INT, WIS, STR, DEX, CHA, PSI
    private int moveSpeed;
    public int[] goalPos = null;

    public Individual(String name){
        super(name);
        stats = new Stats();
        moveSpeed = 100;
    }

    @Override
    public void think() {
        if (goalPos != null) {
            move(goalPos[0], goalPos[1]);
        }
    }

    public void move(int goalx, int goaly) {
        double xdist = goalx - x;
        double ydist = goaly - y;
        double xtravel = 0;
        double ytravel = 0;
        double workingSpeed = Gdx.graphics.getDeltaTime()*moveSpeed;

        if(Math.abs(xdist)>2*workingSpeed||Math.abs(ydist)>2*workingSpeed){
            //if far from goal, go to it
            xtravel=workingSpeed*(Math.abs(xdist)/(Math.abs(xdist)+Math.abs(ydist)));
            ytravel=workingSpeed-xtravel;
            xtravel=Math.ceil(xtravel);
            ytravel=Math.ceil(ytravel);
        }

        //set directions
        if(xdist<0 && xtravel>0){
            xtravel=-xtravel;
        }
        if(ydist<0 && ytravel>0){
            ytravel=-ytravel;
        }

        x+=xtravel;
        y+=ytravel;
    }


    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setGoalPos(int[] goal) {
        this.goalPos = goal;
    }


    public int[] getGoalPos() {
        return goalPos;
    }
}
