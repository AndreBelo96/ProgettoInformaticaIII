package com.example.kokoko.libgdx;

import android.util.Log;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;

public class Rectangle {

    public Vector2 getRectPos() {
        return rectPos;
    }

    private Vector2 rectPos;
    private Texture rectTexture;
    private Vector2 rectVel;
    private float RECT_WIDHT = 14f;
    private float RECT_HEIGHT = 25;
    private boolean grow;

    public void setRectPos(Vector2 rectPos) {
        this.rectPos = rectPos;
    }

    public Rectangle(Vector2 rectPos) {
        this.rectPos = rectPos;
        this.rectTexture = new Texture("rectangle.png");
        rectVel = new Vector2(Constant.WIDTH / 500000, Constant.WIDTH / 220000);
        grow = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(rectTexture, rectPos.x, rectPos.y, RECT_WIDHT / 100f, RECT_HEIGHT / 100f);
        move();
        rotateX();
        rotateY();
    }

    public void move(){
            rectPos.x = rectPos.x + rectVel.x;
            rectPos.y = rectPos.y + rectVel.y;
    }

    public void rotateX() {
        if (RECT_WIDHT > 0 && !grow)
            RECT_WIDHT -= 0.1f;
        else if (RECT_WIDHT < 14 && grow)
            RECT_WIDHT += 0.1f;
        else if (RECT_WIDHT <= 0)
            grow = true;
        else if (RECT_WIDHT >= 14)
            grow = false;
    }

    public void rotateY() {
        if (RECT_HEIGHT > 0 && !grow)
            RECT_HEIGHT -= 0.178571f;
        else if (RECT_HEIGHT < 25 && grow)
            RECT_HEIGHT += 0.178571f;
        else if (RECT_HEIGHT <= 0)
            grow = true;
        else if (RECT_HEIGHT >= 25)
            grow = false;
    }
}

