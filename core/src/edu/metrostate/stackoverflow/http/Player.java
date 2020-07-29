package edu.metrostate.stackoverflow.http;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.jetbrains.annotations.NotNull;

public class Player implements Comparable<Player>{
    private final String name;
    private final int score;
    private static final BitmapFont scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }

    public GlyphLayout getNameInGlyphs() {
        return new GlyphLayout(scoreFont, "" + name);
    }

    public GlyphLayout getScoreInGlyphs() {
        return new GlyphLayout(scoreFont, "" + score);
    }


    @Override
    public int compareTo(@NotNull Player player) {
        return Integer.compare(player.score, this.score);
    }
}
