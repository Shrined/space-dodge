package edu.metrostate.stackoverflow.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.metrostate.stackoverflow.collision.Ship;
import edu.metrostate.stackoverflow.game.SpaceDodge;
import edu.metrostate.stackoverflow.screens.HighScoresScreen;
import edu.metrostate.stackoverflow.screens.MainGameScreen;
import edu.metrostate.stackoverflow.screens.ShipSelectionScreen;

public class MainMenu implements Screen {


    private static final int START_BUTTON_WIDTH =  87;
    private static final int QUIT_BUTTON_WIDTH = 79;
    private static final int BUTTON_HEIGHT =  38;


    SpaceDodge game;
    Ship ship;
    TextureRegion backGround;
    TextureRegion foreground;
    TextureRegion start;
    Texture highScores;
    Texture selectShip;
    TextureRegion quit;
    TextureRegion startHover;
    Texture highScoresHover;
    Texture selectShipHover;
    TextureRegion quitHover;
    TextureRegion floatingRobot;



    public MainMenu(SpaceDodge game) {
        this.game = game;

        //Input the image dimensions
        //Increasing/decreasing dimensions here will warp image on screen
        backGround = new TextureRegion(new Texture("main_menu/background.png"), 1280, 720);
        foreground = new TextureRegion(new Texture("main_menu/MenuForeground.png"), 420, 346);
        start = new TextureRegion(new Texture("main_menu/Start.png"), 87, 38);
        highScores = new Texture("main_menu/Highscores.png");
        selectShip = new Texture("main_menu/SelectShip.png");
        quit = new TextureRegion(new Texture("main_menu/Quit.png"), 79, 38);
        floatingRobot = new TextureRegion(new Texture("main_menu/FloatingRobot.png"), 35, 69);
        startHover = new TextureRegion(new Texture("main_menu/StartClicked.png"), 87, 38);
        highScoresHover = new Texture("main_menu/HighscoresHover.png");
        selectShipHover = new Texture("main_menu/SelectShipHover.png");
        quitHover = new TextureRegion(new Texture("main_menu/QuitClicked.png"), 79, 38);
        ship = new Ship();
    }

    public MainMenu(SpaceDodge game, Ship ship) {
        this(game);
        this.ship = ship;
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
        game.batch.draw(highScores, 525, 325);
        game.batch.draw(selectShip,545, 220);
        game.batch.draw(quit, 575, 160);


        //definitely clean this up
        if((Gdx.input.getX() < 575 + START_BUTTON_WIDTH && Gdx.input.getX() > 575) &&
                ((720 - Gdx.input.getY()) < 400 + BUTTON_HEIGHT && (720 - Gdx.input.getY() > 400))) {
            game.batch.draw(startHover, 575, 400);
            if(Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainGameScreen(game, ship));
            }
        }
        else if((Gdx.input.getX() < 525 + highScores.getWidth() && Gdx.input.getX() > 525) &&
                ((720 - Gdx.input.getY()) < 325 + highScores.getHeight() && (720 - Gdx.input.getY()) > 325)) {
            game.batch.draw(highScoresHover, 525, 325);
            if(Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new HighScoresScreen(game, ship));
            }
        }
        else if((Gdx.input.getX() < 545 + selectShip.getWidth() && Gdx.input.getX() > 545) &&
                ((720 - Gdx.input.getY()) < 220 + selectShip.getHeight() && (720 - Gdx.input.getY()) > 220)) {
            game.batch.draw(selectShipHover, 545, 220);
            if(Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new ShipSelectionScreen(game, ship));
            }
        }
        else if((Gdx.input.getX() < 575 + QUIT_BUTTON_WIDTH && Gdx.input.getX() > 575) &&
                ((720 - Gdx.input.getY()) < 160 + BUTTON_HEIGHT && (720 - Gdx.input.getY()) > 160)) {
            game.batch.draw(quitHover, 575, 160);
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
