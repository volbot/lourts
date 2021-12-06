package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

	private OrthographicCamera cam;
	private SpriteBatch batch;
	Texture grassTex;
	Texture crabwizardTex;
	Rectangle crabwizard;

	int walkSpeed = 2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		grassTex = new Texture("grass.png");
		crabwizardTex = new Texture("crabwizard.png");
		crabwizard =new Rectangle();
		crabwizard.x=320;
		crabwizard.y=240;
		crabwizard.width=20;
		crabwizard.height=20;
		cam = new OrthographicCamera();
		cam.setToOrtho(false,640,480);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		// 640x480 grass grid
		for(int x = 0; x < 640; x+=20){
			for(int y = 0; y < 480; y+=20){
				batch.draw(grassTex,x,y);
			}
		}

		batch.draw(crabwizardTex, crabwizard.x, crabwizard.y);

		batch.end();

		// Arrow keys
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			crabwizard.y+=walkSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			crabwizard.y-=walkSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			crabwizard.x-=walkSpeed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			crabwizard.x+=walkSpeed;
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		grassTex.dispose();
		crabwizardTex.dispose();
	}
}
