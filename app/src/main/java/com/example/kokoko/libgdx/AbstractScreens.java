package com.example.kokoko.libgdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Classe astratta da usare per ogni screen */
public abstract class AbstractScreens extends ScreenAdapter implements Screen {

    private SpriteBatch batch;

    public abstract void render(float deltaTime);

    public void dispose() {
        batch.dispose();
    }

}
