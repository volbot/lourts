package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Hero;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.Input.InputManager;

public class Main extends ApplicationAdapter {

	private OrthographicCamera cam;
	private InputManager inputs;
	private SpriteBatch batch;
	Texture grassTex;
	Texture crabwizardTex;
	Individual crabwizard;
	Hero skeletrex;
	Location boneland;

	@Override
	public void create () {
		batch = new SpriteBatch();
		grassTex = new Texture("grass.png");
		crabwizardTex = new Texture("crabwizard.png");
		crabwizard = new Individual("crabwizard");
		crabwizard.x=0;
		crabwizard.y=0;
		boneland = new Location("crabwizard",200,200);
		skeletrex = boneland.getFigurehead();
		skeletrex.setGoalPos(new int[]{-300, 100});
		skeletrex.setMoveSpeed(140);
		cam = new OrthographicCamera();
		cam.setToOrtho(false,640,480);
		cam.position.x=320;
		cam.position.y=240;
		inputs = new InputManager(cam);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.setProjectionMatrix(cam.combined);

		// 640x480 grass grid
		for(int x = 0; x < 640; x+=20){
			for(int y = 0; y < 480; y+=20){
				batch.draw(grassTex,cam.position.x-cam.viewportWidth/2+x,cam.position.y-cam.viewportHeight/2+y);
			}
		}

		crabwizard.think();
		skeletrex.think();
		inputs.parseCameraMovement(300);
		inputs.parsePlayerMovement(crabwizard);


		batch.draw(crabwizardTex, cam.position.x+boneland.x-10, cam.position.y+boneland.y-10);
		batch.draw(crabwizardTex, cam.position.x+skeletrex.x-10, cam.position.y+skeletrex.y-10);
		batch.draw(crabwizardTex, cam.position.x+crabwizard.x-10, cam.position.y+crabwizard.y-10);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		grassTex.dispose();
		crabwizardTex.dispose();
	}
}
