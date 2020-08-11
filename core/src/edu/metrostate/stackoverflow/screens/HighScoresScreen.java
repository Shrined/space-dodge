package edu.metrostate.stackoverflow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.metrostate.stackoverflow.collision.Ship;
import edu.metrostate.stackoverflow.game.SpaceDodge;
import edu.metrostate.stackoverflow.http.HttpClient;
import edu.metrostate.stackoverflow.http.Player;
import edu.metrostate.stackoverflow.UserInterface.TextBox;
import edu.metrostate.stackoverflow.menus.MainMenu;

import java.io.IOException;

public class HighScoresScreen implements Screen {

    private final HttpClient client;
    private final SpaceDodge game;
    private final BitmapFont scoreFont;
    private final Ship ship;
    private final Texture returnHome = new Texture("high_scores/Return.png");
    private final Texture returnHover = new Texture("high_scores/ReturnHover.png");
    private TextureRegion background;
    private TextBox textBox;
    private Player[] players;
    private int score;
    private boolean posted;

    public HighScoresScreen(SpaceDodge game, int score, Ship ship) {
        this.game = game;
        this.score = score;
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        client = new HttpClient();
        posted = false;
        this.ship = ship;
    }

    public HighScoresScreen(SpaceDodge game, Ship ship) {
        this.game = game;
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        client = new HttpClient();
        posted = true;
        this.ship = ship;
    }

    @Override
    public void show() {
        background = new TextureRegion(new Texture("main_game_screen/Background.png"), 1280, 720);
        textBox = new TextBox();
        if(!posted) {
            Gdx.input.getTextInput(textBox, "Your username: ", "", "");
        }
        try {
            players = client.getTopScores(HttpClient.HIGH_SCORES_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(returnHome, 565, 50);
        checkInput();
        if(!posted) {
            if(textBox.getStatus()) {
                try {
                    if(textBox.getInput() != null) {
                        client.doPostRequest(HttpClient.HIGH_SCORES_URL, client.addScoreJson(textBox.getInput(), String.valueOf(score)));
                    }
                    players = client.getTopScores(HttpClient.HIGH_SCORES_URL);
                    posted = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        renderScores(players);
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

    private void renderScores(Player[] players) {
        if(players.length != 0) {
            for(int i = 0; i < players.length; i++) {
                Player player = players[i];
                scoreFont.draw(game.batch, player.getNameInGlyphs(), 400, 700 - (i * 30));
                scoreFont.draw(game.batch, player.getScoreInGlyphs(), 800, 700 - (i * 30));
            }
        } else {
            scoreFont.draw(game.batch, new GlyphLayout(scoreFont, "Leaderboards are empty"), 300, 400);
        }
    }

    private void checkInput() {
        if((Gdx.input.getX() < 565 + returnHome.getWidth() && Gdx.input.getX() > 565) &&
                ((720 - Gdx.input.getY()) < 50 + returnHome.getHeight() && (720 - Gdx.input.getY() > 50))) {
            game.batch.draw(returnHover, 565, 50);
            if (Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new MainMenu(game, ship));
            }
        }
    }


}
