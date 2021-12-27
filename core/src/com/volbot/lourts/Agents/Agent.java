package com.volbot.lourts.Agents;

import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Data.Reputation;
import com.volbot.lourts.Data.TalkOption;
import com.volbot.lourts.Data.TalkResponse;
import com.volbot.lourts.Main;

import java.util.ArrayList;

public class Agent {

    public int texID;

    public String theme;

    public Vector3 position = new Vector3();

    public final Reputation rep;
    private final String name;

    protected Agent(String name) {
        rep = new Reputation();
        this.name = name;
        this.theme = "base";
    }

    public void interact(Agent a) {
    }

    public void think() { }

    public Location closestLoc() {
        Location closest = null;
        float closestdst = 0f;
        for(Agent a : Main.entities){
            if(a instanceof Location){
                float dst = a.position.dst(this.position);
                if(dst<closestdst || closest==null){
                    closest=(Location)a;
                    closestdst=dst;
                }
            }
        }
        return closest;
    }

    public TalkResponse startConversation(Individual a) {
        if(this.rep.knows(a)) {
            ArrayList<Individual> known = rep.known();
            TalkOption[] searchList = new TalkOption[known.size()+1];
            int i = 0;
            for(Individual a2 : rep.known()){
                if(a2.equals(Main.player)){
                    continue;
                }
                searchList[i++] = new TalkOption("  "+a2.getName(), new TalkResponse(a2.location!=null?
                        a2.getName()+" should be in "+a2.location+".":
                        a2.getName()+" should be near "+a2.closestLoc().getName()+".", new TalkOption[]{new TalkOption("M Thank you.",null)}));
            }
            searchList[i++] = new TalkOption("M Never mind.", null);
            return new TalkResponse("Good to see you, "+a.getName()+".", new TalkOption[]{
                    new TalkOption("  Good to see you too, "+getName()+".",    new TalkResponse("Safe travels.")),
                    new TalkOption("  I'm looking for someone.",    new TalkResponse("Who can I help you find?", searchList)),
                    null,
                    new TalkOption("  I must take my leave. <exit>",            new TalkResponse("Safe travels.")),
            });
        } else {
            this.rep.meet(a);
            a.rep.meet(this);
            return new TalkResponse("I don't believe we've met. I am "+getName(), new TalkOption[]{
                    new TalkOption("+ Pleased to meet you, "+getName()+". I'm "+a.getName(),    new TalkResponse("Great to meet you too, "+a.getName()+". It's always good to see a friendly face out on the field.")),
                    new TalkOption("U Your reputation precedes you, "+getName()+".",            new TalkResponse("I am afraid yours does not...?")),
                    new TalkOption("- Your vibes disgust me.",                                  new TalkResponse("You are a scoundrel.")),
                    new TalkOption("- I must take my leave. <exit>",                            new TalkResponse("Ok... Good day... to you, then?")),
            });
        }
    }

    public int getPopulation() {
        return 1;
    }

    public String getName() {
        return name;
    }
}
