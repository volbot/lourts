package com.volbot.lourts.Map;

import java.util.ArrayList;

public class BattleMap extends GameMap{
    public BattleMap() {
        int i = 0;
        while(i<6){
            chunks.add(new ArrayList<int[][]>());
            i++;
        }
        int[][] grasschunk = new int[24][24];
        i = 0;
        for(int x = 0; x < 24; x++){
            for(int y = 0; y < 24; y++){
                grasschunk[x][y]=i=i==0?0:0;
            }
            i=i==0?0:0;
        }
        for(int x = 0; x < 6; x++){
            for(int y = 0; y < 6; y++){
                chunks.get(x).add(grasschunk);
            }
        }
    }
}
