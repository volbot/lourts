package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

	private OrthographicCamera cam;
	private SpriteBatch batch;
	Texture grass;
	Rectangle grassguy;

	@Override
	public void create () {
		batch = new SpriteBatch();
		grass = new Texture("grass.png");
		grassguy=new Rectangle();
		grassguy.x=0;
		grassguy.y=240;
		grassguy.width=20;
		grassguy.height=20;
		cam = new OrthographicCamera();
		cam.setToOrtho(false,640,480);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		/*//gras moving really fast
		batch.draw(grass,grassguy.x,grassguy.y);
		grassguy.x+=20;
		if(grassguy.x>640) grassguy.x=0;
		*/

		// 640x480 grass grid
		for(int x = 0; x < 640; x+=20){
			for(int y = 0; y < 480; y+=20){
				batch.draw(grass,x,y);
			}
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		grass.dispose();
	}
}
