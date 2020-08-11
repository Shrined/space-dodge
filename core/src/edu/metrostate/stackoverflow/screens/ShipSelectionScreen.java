package edu.metrostate.stackoverflow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.metrostate.stackoverflow.collision.Ship;
import edu.metrostate.stackoverflow.game.SpaceDodge;
import edu.metrostate.stackoverflow.menus.MainMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShipSelectionScreen implements Screen {

    private final SpaceDodge game;
    private Ship ship;
    private float alpha = 1;
    private TextureRegion background;
    private BitmapFont textFont;
    private final Texture leftArrow;
    private final Texture rightArrow;
    private final Texture leftArrowHover;
    private final Texture rightArrowHover;
    private final Texture select;
    private final Texture selectHover;
    private final Texture back;
    private final Texture backHover;
    private final GlyphLayout glyphLayout;


    private static final String SPACE_SHIP_1 = "ships/SpaceShip.png";
    private static final String SPACE_SHIP_2 = "ships/SpaceShip2.png";
    private static final String SPACE_SHIP_3 = "ships/SpaceShip3.png";
    private static final String SPACE_SHIP_4 = "ships/SpaceShip4.png";
    private static final String SPACE_SHIP_5 = "ships/SpaceShip5.png";

    private static final List<Texture> shipTextures = new ArrayList<>();

    private AtomicInteger shipIndex = new AtomicInteger(0);

    static {
        shipTextures.add(new Texture(SPACE_SHIP_1));
        shipTextures.add(new Texture(SPACE_SHIP_2));
        shipTextures.add(new Texture(SPACE_SHIP_3));
        shipTextures.add(new Texture(SPACE_SHIP_4));
        shipTextures.add(new Texture(SPACE_SHIP_5));
    }

    public ShipSelectionScreen(SpaceDodge game, Ship ship) {
        this.game = game;
        this.ship = ship;
        textFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        leftArrow = new Texture("ship_selection/leftArrow.png");
        rightArrow = new Texture("ship_selection/rightArrow.png");
        leftArrowHover = new Texture("ship_selection/leftArrowHover.png");
        rightArrowHover = new Texture("ship_selection/rightArrowHover.png");
        select = new Texture("ship_selection/Select.png");
        selectHover = new Texture("ship_selection/SelectHover.png");
        back = new Texture("ship_selection/Back.png");
        backHover = new Texture("ship_selection/BackHover.png");
        glyphLayout = new GlyphLayout(textFont, "Ship Selected!");
    }



    @Override
    public void show() {
        background = new TextureRegion(new Texture("main_game_screen/Background.png"), 1280, 720);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(leftArrow, 450, 150);
        game.batch.draw(rightArrow, 750, 150);
        game.batch.draw(select, 568, 150);
        game.batch.draw(back, 50, 650);
        checkInput();
        game.batch.draw(shipTextures.get(shipIndex.get()), 600, 400);
        if(alpha != 1) {
            drawText();
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

    private void checkInput() {
        if((Gdx.input.getX() < 450 + leftArrow.getWidth() && Gdx.input.getX() > 450) &&
                ((720 - Gdx.input.getY()) < 150 + leftArrow.getHeight() && (720 - Gdx.input.getY() > 150))) {
            game.batch.draw(leftArrowHover, 450, 150);
            if(Gdx.input.justTouched()) {
                shipIndex.getAndDecrement();
                if(shipIndex.get() == -1) {
                    shipIndex.set(4);
                }
            }
        }
        if((Gdx.input.getX() < 750 + rightArrow.getWidth() && Gdx.input.getX() > 750) &&
                ((720 - Gdx.input.getY()) < 150 + rightArrow.getHeight() && (720 - Gdx.input.getY() > 150))) {
            game.batch.draw(rightArrowHover, 750, 150);
            if(Gdx.input.justTouched()) {
                shipIndex.getAndIncrement();
                if(shipIndex.get() == 5) {
                    shipIndex.set(0);
                }
            }
        }
        if((Gdx.input.getX() < 568 + select.getWidth() && Gdx.input.getX() > 568) &&
                ((720 - Gdx.input.getY()) < 150 + select.getHeight() && (720 - Gdx.input.getY() > 150))) {
            game.batch.draw(selectHover, 568, 150);
            if (Gdx.input.justTouched()) {
                drawText();
                ship = new Ship(shipTextures.get(shipIndex.get()));
            }
        }
        if((Gdx.input.getX() < 50 + select.getWidth() && Gdx.input.getX() > 50) &&
                ((720 - Gdx.input.getY()) < 650 + select.getHeight() && (720 - Gdx.input.getY() > 650))) {
            game.batch.draw(backHover, 50, 650);
            if (Gdx.input.justTouched()) {
                this.dispose();
                game.setScreen(new MainMenu(game, ship));
            }
        }

    }

    private void drawText() {
        alpha += (1f / 60f) / 2;
        textFont.draw(game.batch, glyphLayout, 450, 700);
        if(alpha > 2) {
            alpha = 1;
        }
    }

}
