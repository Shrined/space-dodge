package edu.metrostate.stackoverflow.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.metrostate.stackoverflow.game.SpaceDodge;
import edu.metrostate.stackoverflow.screens.MainGameScreen;

public class MainMenu implements Screen {


    private static final int START_BUTTON_WIDTH =  87;
    private static final int SETTINGS_BUTTON_WIDTH = 133;
    private static final int QUIT_BUTTON_WIDTH = 79;
    private static final int BUTTON_HEIGHT =  38;


    SpaceDodge game;
    TextureRegion backGround;
    TextureRegion foreground;
    TextureRegion start;
    TextureRegion settings;
    TextureRegion quit;
    TextureRegion startHover;
    TextureRegion settingsHover;
    TextureRegion quitHover;
    TextureRegion floatingRobot;



    public MainMenu(SpaceDodge game) {
        this.game = game;

        //Input the image dimensions
        //Increasing/decreasing dimensions here will warp image on screen
        backGround = new TextureRegion(new Texture("main_menu/background.png"), 1280, 720);
        foreground = new TextureRegion(new Texture("main_menu/MenuForeground.png"), 420, 346);
        start = new TextureRegion(new Texture("main_menu/Start.png"), 87, 38);
        settings = new TextureRegion(new Texture("main_menu/Settings.png"), 133, 38);
        quit = new TextureRegion(new Texture("main_menu/Quit.png"), 79, 38);
        floatingRobot = new TextureRegion(new Texture("main_menu/FloatingRobot.png"), 35, 69);
        startHover = new TextureRegion(new Texture("main_menu/StartClicked.png"), 87, 38);
        settingsHover = new TextureRegion(new Texture("main_menu/SettingsClicked.png"), 133, 38);
        quitHover = new TextureRegion(new Texture("main_menu/QuitClicked.png"), 79, 38);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        //0,0 is the bottom left of the window
        //Note: to push image to front, have it drawn last

        //Love to not have these hard coded...
        game.batch.draw(backGround, 0, 0);
        game.batch.draw(floatingRobot, 605, 635);
        game.batch.draw(foreground, 440, 320);
        game.batch.draw(start, 575, 400);
        game.batch.draw(settings, 560, 325);
        game.batch.draw(quit, 580, 250);


        //definitely clean this up
        if((Gdx.input.getX() < 575 + START_BUTTON_WIDTH && Gdx.input.getX() > 575) &&
                ((720 - Gdx.input.getY()) < 400 + BUTTON_HEIGHT && (720 - Gdx.input.getY() > 400))) {
            game.batch.draw(startHover, 575, 400);
            if(Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainGameScreen(game));
            }
        }
        else if((Gdx.input.getX() < 560 + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > 560) &&
                ((720 - Gdx.input.getY()) < 325 + BUTTON_HEIGHT && (720 - Gdx.input.getY()) > 325)) {
            game.batch.draw(settingsHover, 560, 325);
        }
        else if((Gdx.input.getX() < 580 + QUIT_BUTTON_WIDTH && Gdx.input.getX() > 580) &&
                ((720 - Gdx.input.getY()) < 250 + BUTTON_HEIGHT && (720 - Gdx.input.getY()) > 250)) {
            game.batch.draw(quitHover, 580, 250);
            if(Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
