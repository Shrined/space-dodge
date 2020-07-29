package edu.metrostate.stackoverflow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.metrostate.stackoverflow.game.SpaceDodge;
import edu.metrostate.stackoverflow.http.HttpClient;
import edu.metrostate.stackoverflow.http.Player;
import edu.metrostate.stackoverflow.UserInterface.TextBox;

import java.io.IOException;
import java.util.List;

public class HighScoresScreen implements Screen {

    private final HttpClient client;
    private final SpaceDodge game;
    private final BitmapFont scoreFont;
    private TextureRegion background;
    private TextBox textBox;
    private List<Player> players;
    private int score;
    private boolean posted;

    public HighScoresScreen(SpaceDodge game, int score) {
        this.game = game;
        this.score = score;
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        client = new HttpClient();
        posted = false;
    }

    @Override
    public void show() {
        background = new TextureRegion(new Texture("main_game_screen/Background.png"), 1280, 720);
        textBox = new TextBox();
        Gdx.input.getTextInput(textBox, "Your username: ", "", "");
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
        if(!posted) {
            if(textBox.getStatus()) {
                try {
                    client.doPostRequest(HttpClient.HIGH_SCORES_URL, client.addScoreJson(textBox.getInput(), String.valueOf(score)));
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

    private void renderScores(List<Player> players) {
        if(players != null) {
            for(int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                scoreFont.draw(game.batch, player.getNameInGlyphs(), 400, 700 - (i * 30));
                scoreFont.draw(game.batch, player.getScoreInGlyphs(), 800, 700 - (i * 30));
            }
        }
    }


}
