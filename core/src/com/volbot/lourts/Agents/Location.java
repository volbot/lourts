package com.volbot.lourts.Agents;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Hero;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Data.Personality;
import com.volbot.lourts.Data.TalkOption;
import com.volbot.lourts.Data.TalkResponse;

import java.util.ArrayList;
import java.util.Random;

public class Location extends Agent {
    private Hero figurehead;
    private int population;
    private int wealth;
    private Personality personality;

    public ArrayList<Individual> heroes = new ArrayList<>();

    public Location(String name, int xpos, int ypos){
        super(name);
        population=1;
        wealth=0;
        x=xpos;
        y=ypos;
        figurehead=new Hero("Skeletrex",this);
        figurehead.texID=0;
        personality=figurehead.getPersonality();
    }

    public Location(String name, int xpos, int ypos, int population){
        super(name);
        this.population=population;
        wealth=0;
        x=xpos;
        y=ypos;
        figurehead=new Hero("Skeletrex",this);
        figurehead.texID=0;
        personality=figurehead.getPersonality();
    }

    public Location(String name) {
        super(name);
        this.population=1;
        wealth=0;
        figurehead=new Hero("Skeletrex",this);
        figurehead.texID=0;
        personality=figurehead.getPersonality();
    }

    public TalkResponse startConversation(Individual a) {
            if (rep.knows(a)) {
                int recruits = potentialRecruits(a);
                TalkOption[] promptOptions = new TalkOption[4];
                TalkResponse recruitResponse = new TalkResponse(recruits>0?"Hmm... If memory serves me right, I can think of "+recruits+" able-bodied citizens.":"No, I am afraid not.",
                        new TalkOption[]{
                                new TalkOption("R I'd like to recruit all "+recruits+".", new TalkResponse("Ok.")),
                                new TalkOption("M Never mind.", null)
                        });
                promptOptions = new TalkOption[]{
                        new TalkOption("  Do you know of any people looking to join a caravan? ", recruitResponse),
                        new TalkOption("T Which of these many, many sidewalks gets me to the market? ", new TalkResponse("None of them.")),
                        new TalkOption("- Good to see you too, " + getName() + ".", new TalkResponse("Safe travels.")),
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
        population+=new Random().nextInt((2*population) + 1) - population;
        if(population==0){
            //towndies
        }
    }

    public int potentialRecruits(Individual a) {
        int reputation = rep.get(a);
        //
        if(reputation < -20 || population <= 12) return 0;
        float repPercent = ((reputation + 20) / 120f);
        float popPercent = 0.4f;
        int fightingPop = Math.round(popPercent*population);
        return Math.round(repPercent*fightingPop);
    }

    public Personality getPersonality() {
        return personality;
    }

    public int getPopulation() {
        return population;
    }

    public Hero getFigurehead() {
        return figurehead;
    }

    public int getWealth() {
        return wealth;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setFigurehead(Hero figurehead) {
        this.figurehead = figurehead;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }
}
