package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;

public class Rectangle {

    private Vector2 rectPos;
    private Texture rectTexture;

    public Rectangle(Vector2 rectPos) {
        this.rectPos = rectPos;
        this.rectTexture = new Texture("off.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(rectTexture, rectPos.x, rectPos.y, Constant.RECT_WIDHT, Constant.RECT_HEIGHT);
    }
}

