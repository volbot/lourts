package com.volbot.lourts.Agents;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Hero;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Data.Personality;
import com.volbot.lourts.Data.TalkOption;
import com.volbot.lourts.Data.TalkResponse;
import com.volbot.lourts.Main;

import java.util.ArrayList;
import java.util.Random;

public class Location extends Agent {
    private Hero figurehead;
    private int wealth;
    private Personality personality;

    public ArrayList<Individual> heroes = new ArrayList<>();

    public Location(String name, String figureheadname, Faction faction, int xpos, int ypos, int population){
        super(name);
        this.faction=faction;
        wealth=0;
        position.x=xpos;
        position.y=ypos;
        figurehead=new Hero(figureheadname,this);
        figurehead.faction=faction;
        figurehead.texID=1;
        figurehead.getParty().add(new Demographic(this,population/8));
        personality=figurehead.getPersonality();
        this.population.add(new Demographic(this,population));
    }

    public Location(String name, String figureheadname, int xpos, int ypos, int population){
        super(name);
        this.faction=null;
        wealth=0;
        position.x=xpos;
        position.y=ypos;
        figurehead=new Hero(figureheadname,this);
        figurehead.texID=1;
        figurehead.getParty().add(new Demographic(this,population/8));
        personality=figurehead.getPersonality();
        this.population.add(new Demographic(this,population));
    }

    public Location(String name) {
        super(name);
        wealth=0;
        figurehead=new Hero("Skeletrex",this);
        figurehead.texID=1;
        personality=figurehead.getPersonality();
    }

    public TalkResponse startConversation(Individual a) {
            if (rep.knows(a)) {
                int recruits = potentialRecruits(a);
                TalkOption[] promptOptions;
                TalkResponse recruitResponse = new TalkResponse(recruits>0?"Hmm... If memory serves me right, I can think of "+recruits+" able-bodied citizens.":"No, I am afraid not.",
                        new TalkOption[]{
                                new TalkOption("R I'd like to recruit all "+recruits+".",
                                        new TalkResponse("I'll notify them; they should be ready to leave when you are.",
                                        new TalkOption[]{new TalkOption("M Thank you.",null)})),
                                new TalkOption("M Never mind.", null)
                        });
                if(recruits==0){
                    recruitResponse.options=new TalkOption[]{recruitResponse.options[1]};
                }
                promptOptions = new TalkOption[]{
                        new TalkOption("  Do you know of any people looking to join a caravan? ", recruitResponse),
                        new TalkOption("T Which of these many, many sidewalks gets me to the market? ", new TalkResponse("None of them.")),
                        null,
                        new TalkOption("  I must take my leave. <exit>", new TalkResponse("Safe travels.")),
                };
                return new TalkResponse("Good to see you, " + a.getName() + ". Welcome back.", promptOptions);
            } else {
                rep.meet(a);
                a.rep.meet(this);
                return new TalkResponse("I have not seen you around these parts. Welcome to " + getName() + ".", new TalkOption[]{
                        new TalkOption("+ I love your intricate sidewalks. I'm " + a.getName(), new TalkResponse("I'm so glad you noticed! Make yourself at home.")),
                        new TalkOption("  Interesting sidewalk design. Very proto-post-intersection-core.", new TalkResponse("....")),
                        new TalkOption("- These sidewalks are horrible; far too intricate.", new TalkResponse("You need to leave.")),
                        new TalkOption("- I must take my leave. <exit>", new TalkResponse("Ok... have... a good one?")),
                });
            }
    }

    @Override
    public void think() {

    }

    @Override
    public void setFaction(Faction faction) {
        super.setFaction(faction);
        this.getFigurehead().setFaction(faction);
    }

    public int potentialRecruits(Individual a) {
        int reputation = rep.get(a);
        if(reputation < -20) return 0;
        float repPercent = ((reputation + 20) / 120f);
        float popPercent = 0.4f;
        int fightingPop = Math.round(popPercent*getPopulationSize());
        if(fightingPop<15) return 0;
        return Math.round(repPercent*fightingPop);
    }

    public Demographic recruit(int num){
        Demographic d = new Demographic(this,num);
        population.sub(d);
        return d;
    }

    public Personality getPersonality() {
        return personality;
    }

    public Hero getFigurehead() {
        return figurehead;
    }

    public int getWealth() {
        return wealth;
    }

    public void setFigurehead(Hero figurehead) {
        this.figurehead = figurehead;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }
}
