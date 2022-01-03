package com.volbot.lourts.Map;

import java.util.ArrayList;

public class GameMap {
    public ArrayList<ArrayList<int[][]>> chunks = new ArrayList<>();

    public GameMap() {
        int i = 0;
        while(i<4){
            chunks.add(new ArrayList<int[][]>());
            i++;
        }
        int[][] grasschunk = new int[24][24];
        for(int x = 0; x < 24; x++){
            for(int y = 0; y < 24; y++){
                grasschunk[x][y]=0;
            };
        }
        int[][] waterchunk = new int[24][24];
        for(int x = 0; x < 24; x++){
            for(int y = 0; y < 24; y++){
                waterchunk[x][y]=2;
            }
        }
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                chunks.get(x).add(grasschunk);
            }
        }
        chunks.get(1).set(1,waterchunk);
    }

    public int tileAt(float x, float y) {
        int chunksx = 0;
        float temp = x;
        while ((temp-=480)>0) chunksx++;
        int chunksy = 0;
        temp = y;
        while ((temp-=480)>0) chunksy++;
        int xfinal = (int)Math.floor((x-chunksx*480)/20);
        int yfinal = (int)Math.floor((y-chunksy*480)/20);
        return chunks.get(chunksx).get(chunksy)[xfinal][yfinal];
    }
}
