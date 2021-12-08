package com.volbot.lourts.Map;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMap {
    public ArrayList<ArrayList<int[][]>> chunks = new ArrayList<>();

    public GameMap() {
        int i = 0;
        while(i<4){
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
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                chunks.get(x).add(grasschunk);
            }
        }
    }
}
