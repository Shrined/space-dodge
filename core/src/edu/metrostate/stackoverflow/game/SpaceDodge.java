package edu.metrostate.stackoverflow.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.metrostate.stackoverflow.menus.MainMenu;

public class SpaceDodge extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
