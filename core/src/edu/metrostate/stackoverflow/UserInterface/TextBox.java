package edu.metrostate.stackoverflow.UserInterface;

import com.badlogic.gdx.Input.TextInputListener;

public class TextBox implements TextInputListener {

    private String input;
    private boolean interactionComplete = false;

    @Override
    public void input(String text) {
        this.input = text;
        interactionComplete = true;
    }

    @Override
    public void canceled() {
        interactionComplete = true;
    }

    public String getInput() {
        return input;
    }

    public boolean getStatus() {
        return interactionComplete;
    }
}
