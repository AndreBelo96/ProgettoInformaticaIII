package com.example.kokoko.libgdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screens extends ScreenAdapter implements Screen {

    public GameClass gameClass;
    private SpriteBatch batch;

    public abstract void render(float dt);
    public void dispose(){
        batch.dispose();
    }

}
