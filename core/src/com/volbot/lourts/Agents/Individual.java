package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Data.Stats;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.Main;

public class Individual extends Agent{
    private final Stats stats; //INT, WIS, STR, DEX, CHA, PSI
    private int moveSpeed;
    public Vector3 goalPos;
    public Agent dest;

    public Individual(String name){
        super(name);
        stats = new Stats();
        moveSpeed = 100;
        goalPos=null;
        dest=null;
    }

    @Override
    public void think() {
        if(this.getDestination()!=null){
            Vector3 destLoc = new Vector3(dest.x,dest.y,0);
            if(!move(destLoc)){
                if(Main.gui.currmenu!=null){
                    InteractMenu menu = Main.gui.currmenu;
                    if(menu.buttons[0].isChecked()){
                        System.out.println("TALK!");
                        Main.gui.clearInteractMenu();
                        dest=null;
                    } else if(menu.buttons[1].isChecked()){
                        System.out.println("FIGHT!");
                        Main.gui.clearInteractMenu();
                        dest=null;
                    }
                } else {
                    Main.gui.drawInteractMenu(dest);
                }
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

    public void setDestination(Vector3 goalPos) {
        this.dest = null;
        this.goalPos = goalPos;
    }
    public Agent getDestination(){
        return this.dest;
    }

    public boolean move(Vector3 goal) {
        double xdist = goal.x - x;
        double ydist = goal.y - y;
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

        boolean returnval = false;
        if(Math.abs(xdist)>10){
            x+=xtravel;
            returnval=true;
        }
        if(Math.abs(ydist)>10){
            y+=ytravel;
            returnval=true;
        }
        return returnval;
    }
}
