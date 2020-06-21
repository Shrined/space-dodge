package edu.metrostate.stackoverflow.collision;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class CollidingObject extends Collision {

    private Texture texture;

    public CollidingObject(Texture texture, int x, int y) {
        super(x, y, texture.getWidth(), texture.getHeight());
        this.texture = texture;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, getX(), getY());
    }

    public Texture getTexture() {
        return texture;
    }

}
