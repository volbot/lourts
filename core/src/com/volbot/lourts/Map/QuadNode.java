package com.volbot.lourts.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Main;

public class QuadNode {

    protected Perlin rand = new Perlin();

    QuadNode bl;
    QuadNode tl;
    QuadNode br;
    QuadNode tr;
    public Tile tile = null;
    public int depth;
    public int xkey;
    public int ykey;
    public int height;

    public QuadNode(int depth, int x, int y, Tile tile) {
        this.depth = depth;
        this.xkey = x;
        this.ykey = y;

        if (depth > 0) {
            int len = (int) Math.pow(2, depth);
            bl = new QuadNode(depth - 1, xkey, ykey, tile);
            br = new QuadNode(depth - 1, xkey + (len / 2), ykey, tile);
            tl = new QuadNode(depth - 1, xkey, ykey + (len / 2), tile);
            tr = new QuadNode(depth - 1, xkey + (len / 2), ykey + (len / 2), tile);
        } else {
            Vector3 seed = new Vector3(xkey, ykey, 0);
            float temp = rand.noise(seed.cpy().scl(20));
            this.height = (int) temp;
            this.tile = tile;
        }
    }

    public void insert(int x, int y, Tile tile) {
        if (depth > 0) {
            int len = (int) Math.pow(2, depth) / 2;
            if (!(x > bl.xkey + len || x < bl.xkey || y > bl.ykey + len || y < bl.ykey)) {
                bl.insert(x, y, tile);
            }
            if (!(x > br.xkey + len || x < br.xkey || y > br.ykey + len || y < br.ykey)) {
                br.insert(x, y, tile);
            }
            if (!(x > tl.xkey + len || x < tl.xkey || y > tl.ykey + len || y < tl.ykey)) {
                tl.insert(x, y, tile);
            }
            if (!(x > tr.xkey + len || x < tr.xkey || y > tr.ykey + len || y < tr.ykey)) {
                tr.insert(x, y, tile);
            }
        } else {
            if (x == xkey && y == ykey) this.tile = tile;
        }

    }

    public void insert(QuadNode node) {
        if (node.depth > 0) {
            int len = (int) Math.pow(2, depth) / 2;
            int x = node.xkey;
            int y = node.ykey;
            insert(node.bl);
            insert(node.br);
            insert(node.tl);
            insert(node.tr);
            /*
            if (!(x > bl.xkey + len || x < bl.xkey || y > bl.ykey + len || y < bl.ykey)) {
                System.out.println("bl"+depth);
                insert(node.bl);
            }
            if (!(x > br.xkey + len || x < br.xkey || y > br.ykey + len || y < br.ykey)) {
                System.out.println("br"+depth);
                insert(node.br);
            }
            if (!(x > tl.xkey + len || x < tl.xkey || y > tl.ykey + len || y < tl.ykey)) {
                System.out.println("tl"+depth);
                insert(node.tl);
            }
            if (!(x > tr.xkey + len || x < tr.xkey || y > tr.ykey + len || y < tr.ykey)) {
                System.out.println("tr"+depth);
                insert(node.tr);
            }
             */
        } else {
            insert(node.xkey,node.ykey,node.tile);
        }
    }

    public void drawNode(SpriteBatch batch, OrthographicCamera cam) {
        float xdraw = 20 * cam.zoom * xkey;
        float ydraw = 20 * cam.zoom * ykey;
        xdraw += cam.position.x;
        ydraw += cam.position.y;
        if (depth == 0) {
            if (tile != null) {
                if (tile.texID.equals("grass") && cam.zoom > 0.2)
                    batch.setColor(1f, 1f, 1f, ((height / 3f) + 66) / 100f);
                batch.draw(Main.texLoader.tiles.get(tile.texID), xdraw, ydraw, 20 * cam.zoom, 20 * cam.zoom);
                batch.setColor(Color.WHITE);
            }
        } else {
            if (xdraw < cam.position.x || ydraw < cam.position.y) return;
            if (xdraw + cam.position.x > cam.position.x + cam.viewportWidth || ydraw + cam.position.y > cam.position.y + cam.viewportHeight)
                return;

            tl.drawNode(batch, cam);
            tr.drawNode(batch, cam);
            br.drawNode(batch, cam);
            bl.drawNode(batch, cam);
        }
    }

    public Tile getTile(int xIn, int yIn) {
        int x = xIn;
        int y = yIn;
        if (depth == 0) {
            return tile;
        } else {
            int len = (int) Math.pow(2, depth) / 2;
            if (x >= bl.xkey && x < br.xkey && y >= bl.ykey && y < tl.ykey) {
                return bl.getTile(x, y);
            }
            if (x >= br.xkey && x < br.xkey + len && y >= br.ykey && y < tr.ykey) {
                return br.getTile(x, y);
            }
            if (x >= tr.xkey && x < tr.xkey + len && y >= tr.ykey && y < tr.ykey + len) {
                return tr.getTile(x, y);
            }
            if (x >= tl.xkey && x < tr.xkey && y >= tl.ykey && y < tl.ykey + len) {
                return tl.getTile(x, y);
            }
            return new Tile("block");
        }
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
}