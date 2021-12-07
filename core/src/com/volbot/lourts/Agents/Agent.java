package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.volbot.lourts.Data.Reputation;

public class Agent {
    private final Reputation rep;
    private String name;
    public int x;
    public int y;
    private int moveSpeed;
    public int[] goal = null;

    public Agent(String name) {
        super();
        rep = new Reputation();
        this.name = name;
        moveSpeed = 140;
    }

    public int[] getGoal() {
        return goal;
    }

    public void think() {
        System.out.println(x+"  "+y);
        if (goal != null) {
            move(goal[0], goal[1]);
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

    public void setGoal(int[] goal) {
        this.goal = goal;
    }

    public String getName() {
        return name;
    }

    public int getReputation(Agent agent) {
        if (rep.isset(agent.getName())) {
            return rep.get(agent.getName());
        }
        return rep.get(agent.getName());
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
