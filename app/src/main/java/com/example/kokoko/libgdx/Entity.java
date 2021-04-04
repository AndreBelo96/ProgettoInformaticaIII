package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Interfaccia per  il giocatore*/
public interface Entity {

    void render(SpriteBatch batch);

    void update(float delta);
}
