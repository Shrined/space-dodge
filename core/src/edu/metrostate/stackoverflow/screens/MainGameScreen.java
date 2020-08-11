package edu.metrostate.stackoverflow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.metrostate.stackoverflow.collision.Collision;
import edu.metrostate.stackoverflow.collision.CollidingObject;
import edu.metrostate.stackoverflow.collision.Ship;
import edu.metrostate.stackoverflow.game.SpaceDodge;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainGameScreen implements Screen {

    private static final int SHIP_SPEED = 8;
    private static final int BULLET_SPEED = 10;
    private static int COLLISION_OBJECT_SPEED = 0;

    private final SpaceDodge game;
    private TextureRegion background;
    private Ship ship;
    private int shipY;
    private int shipX;
    private long tStart;
    private int score;
    private final ConcurrentLinkedQueue<CollidingObject> asteroids;
    private static final Random random = new Random();
    private final BitmapFont scoreFont;
    private final GlyphLayout scoreLayout;

    public MainGameScreen(SpaceDodge game, Ship ship) {
        this.game = game;
        asteroids = new ConcurrentLinkedQueue<>();
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        scoreLayout = new GlyphLayout(scoreFont, "" + score);
        this.ship = ship;
        score = 0;
    }

    @Override
    public void show() {
        background = new TextureRegion(new Texture("main_game_screen/Background.png"), 1280, 720);
        tStart = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        updateShipPosition();
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        ship.render(game.batch);
        for(CollidingObject collidingObject : asteroids) {
            collidingObject.render(game.batch);
        }
        scoreFont.draw(game.batch, scoreLayout, 1100, 700);
        if(checkShipCollision(ship, asteroids)) {
            gameOver();
        }
        game.batch.end();
        checkBulletCollision();
        despawnCollision();
        updateCollisionCords();
        if(asteroids.size() < 10) {
            spawnCollision();
        }
        updateScore();
        scoreLayout.setText(scoreFont, "" + score);
        updateGameDifficulty();
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

    private void updateShipPosition() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            shipY += SHIP_SPEED + Gdx.graphics.getDeltaTime();
            if(shipY > 660) { // making sure the ship doesn't go off the screen
                shipY = 660;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            shipY -= SHIP_SPEED + Gdx.graphics.getDeltaTime();
            if(shipY < 0) {
                shipY = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            shipX += SHIP_SPEED + Gdx.graphics.getDeltaTime();
            if(shipX > 500) {
                shipX = 500;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            shipX -= SHIP_SPEED + Gdx.graphics.getDeltaTime();
            if(shipX < 0) {
                shipX = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            ship.shoot();
        }
        ship.updatePos(shipX, shipY);
    }

    private void spawnCollision() {
        int yCord;
        int xCord;
        while(true) {
            yCord = (random.nextInt(660));
            xCord = 1280 + random.nextInt(1280);
            boolean isSpacedOut = true;
            for(CollidingObject colObj: asteroids) {
                if(colObj.getY() - yCord < 100 && colObj.getY() - yCord > -100) {
                    if(colObj.getX() - xCord < 150 && colObj.getX() - xCord > -150) {
                        isSpacedOut = false;
                        break;
                    }
                }
            }
            if(isSpacedOut) {
                asteroids.add(new CollidingObject(getAsteroidTexture(random.nextInt(3)), xCord, yCord));
                break;
            }
        }
    }

    private void despawnCollision() {
        for(CollidingObject asteroid : asteroids) {
            if(asteroid.getX() <= 0) {
                asteroids.remove(asteroid);
            }
        }
        for(CollidingObject bullet : ship.getBullets()) {
            if(bullet.getX() >= 1280) {
                ship.getBullets().remove(bullet);
            }
        }
    }

    private void updateCollisionCords() {
        for(CollidingObject asteroid : asteroids) {
            asteroid.updatePos(asteroid.getX() - COLLISION_OBJECT_SPEED, asteroid.getY());
        }
        for(CollidingObject bullet : ship.getBullets()) {
            bullet.updatePos(bullet.getX() + BULLET_SPEED, bullet.getY());
        }
    }

    private void updateGameDifficulty() {
        int level = (Math.round(getTotalSeconds()) / 20) + 5; //increase game difficulty every 20 seconds
        COLLISION_OBJECT_SPEED = level;
    }

    private void gameOver() {
        this.dispose();
        game.setScreen(new HighScoresScreen(game, score, ship));
    }

    private boolean checkShipCollision(Collision ship, ConcurrentLinkedQueue<CollidingObject> collidingObjects) {
        for(Collision cObj: collidingObjects) {
            if(ship.collidesWith(cObj)) {
                return true;
            }
        }
        return false;
    }

    private void checkBulletCollision() {
        if(!ship.getBullets().isEmpty()) {
            for(CollidingObject bullet: ship.getBullets()) {
                for(CollidingObject asteroid: asteroids) {
                    if(bullet.collidesWith(asteroid)) {
                        asteroids.remove(asteroid);// TODO: make an explosion effect instead of just removing
                        ship.getBullets().remove(bullet);
                        break;
                    }
                }
            }
        }
    }

    private long getElapsedTime() {
        return System.currentTimeMillis() - tStart;
    }

    private float getTotalSeconds() {
        float totalSeconds = getElapsedTime() / 1000.0f;
        return totalSeconds;
    }

    public void updateScore() {
        score = Math.round(getElapsedTime() / 100.0f);
    }

    private Texture getAsteroidTexture(int num) {
        switch (num) {
            case 0:
                return new Texture("collision/Asteroid1.png");
            case 1:
                return new Texture("collision/Asteroid2.png");
            case 2:
                return new Texture("collision/Asteroid3.png");
        }
        return null;
    }
}
