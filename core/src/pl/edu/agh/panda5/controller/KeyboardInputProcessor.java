package pl.edu.agh.panda5.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

// Currently not used
public class KeyboardInputProcessor implements InputProcessor {
    public boolean keyDown (int keycode) {
        if(keycode == Input.Keys.UP)
            System.out.println("Button up ");

        return false;
    }

    public boolean keyUp (int keycode) {
        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }

}
