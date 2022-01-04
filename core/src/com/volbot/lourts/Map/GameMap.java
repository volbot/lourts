package com.volbot.lourts.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.volbot.lourts.Main;

public class GameMap {

    public class QuadNode {
        QuadNode bl;
        QuadNode tl;
        QuadNode br;
        QuadNode tr;
        public Tile tile = null;
        public int depth;
        public int xkey;
        public int ykey;

        public QuadNode(int depth) {
            this.depth=depth;
            this.xkey=0;
            this.ykey=0;
            if (depth > 0) {
                int len = (int)Math.pow(2,depth);
                bl = new QuadNode(depth - 1, xkey, ykey);
                br = new QuadNode(depth - 1, xkey+(len/2),ykey);
                tl = new QuadNode(depth - 1, xkey, ykey+(len/2));
                tr = new QuadNode(depth - 1, xkey+(len/2),ykey+(len/2));
            } else {
                tile = new Tile("grass");
            }
        }

        public QuadNode(int depth, int x, int y) {

            this.depth=depth;
            this.xkey=x;
            this.ykey=y;

            if (depth > 0) {
                int len = (int)Math.pow(2,depth);
                bl = new QuadNode(depth - 1, xkey, ykey);
                br = new QuadNode(depth - 1, xkey+(len/2),ykey);
                tl = new QuadNode(depth - 1, xkey, ykey+(len/2));
                tr = new QuadNode(depth - 1, xkey+(len/2),ykey+(len/2));
            } else {
                tile = new Tile("grass");
            }
        }

        public void insert(int x, int y, Tile tile) {
            if(depth>0){
                int len = (int)Math.pow(2,depth)/2;
                if(!(x>bl.xkey+len || x<bl.xkey || y>bl.ykey+len || y<bl.ykey)){
                    bl.insert(x,y,tile);
                }
                if(!(x>br.xkey+len || x<br.xkey || y>br.ykey+len || y<br.ykey)){
                    br.insert(x,y,tile);
                }
                if(!(x>tl.xkey+len || x<tl.xkey || y>tl.ykey+len || y<tl.ykey)){
                    tl.insert(x,y,tile);
                }
                if(!(x>tr.xkey+len || x<tr.xkey || y>tr.ykey+len || y<tr.ykey)){
                    tr.insert(x,y,tile);
                }
            } else {
                if(x==xkey&&y==ykey) this.tile=tile;
            }

        }

        public void drawNode(SpriteBatch batch, OrthographicCamera cam) {
            float xdraw = 20*cam.zoom*xkey;
            float ydraw = 20*cam.zoom*ykey;
            xdraw+=cam.position.x;
            ydraw+=cam.position.y;

            if(depth==0){
                batch.draw(Main.texLoader.tiles.get(tile.texID),xdraw,ydraw,20*cam.zoom,20*cam.zoom);
            } else {
                if(xdraw<cam.position.x || ydraw<cam.position.y){
                    return;
                }
                if (xdraw > cam.position.x + cam.viewportWidth || ydraw > cam.position.y + cam.viewportHeight) {
                    return;
                }
                tl.drawNode(batch,cam);
                tr.drawNode(batch,cam);
                br.drawNode(batch,cam);
                bl.drawNode(batch,cam);
            }
        }

        public QuadNode setTile(Tile t) {
            this.tile = t;
            return this;
        }

        public QuadNode get(int i) {
            switch (i) {
                case 0:
                    return tl;
                case 1:
                    return tr;
                case 2:
                    return br;
                case 3:
                    return bl;
            }
            return null;
        }


        public QuadNode set(int i, QuadNode node) {
            switch (i) {
                case 0:
                    tl=node;
                    break;
                case 1:
                    tr=node;
                    break;
                case 2:
                    br=node;
                    break;
                case 3:
                    bl=node;
                    break;
            }
            return this;
        }
    }

    public QuadNode chunks;

    public GameMap() {
        chunks = new QuadNode(6);
        chunks.insert(6,6,new Tile("water"));
        chunks.insert(2,6,new Tile("water"));
        chunks.insert(1,3,new Tile("water"));
        chunks.insert(7,3,new Tile("water"));
        chunks.insert(6,2,new Tile("water"));
        chunks.insert(5,2,new Tile("water"));
        chunks.insert(4,2,new Tile("water"));
        chunks.insert(3,2,new Tile("water"));
        chunks.insert(2,2,new Tile("water"));
    }

    public int size() {
        return chunks.depth*4;
    }
}
