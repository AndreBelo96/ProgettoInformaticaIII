package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;

public class Background {

    private Rectangle[][] rectMat;

    public Background() {

        rectMat = new Rectangle[5][10];

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0)
                    rectMat[i][j] = new Rectangle(new Vector2(((j * Constant.WIDTH / 4) - (2 * Constant.WIDTH)) / 1000, ((i * Constant.HEIGHT / 2) - (Constant.HEIGHT)) / 1000));
                else
                    rectMat[i][j] = new Rectangle(new Vector2((((Constant.WIDTH / 8) + (j * Constant.WIDTH / 4)) - (2 * Constant.WIDTH)) / 1000, ((i * Constant.HEIGHT / 2) - (Constant.HEIGHT)) / 1000));
            }
    }

    public void render(SpriteBatch batch) {
        for (Rectangle[] rect: rectMat)
            for (Rectangle elem: rect) {
                elem.render(batch); //controlla che funzioni o se devi cambiare metodo
            }
    }

    public void moveRect() {
        for (Rectangle[] rect: rectMat) {
            if (rect[0].getRectPos().y > ((Constant.HEIGHT) + Constant.RECT_HEIGHT / 2) / 1000) {
                int j = 0;
                for (Rectangle elem : rect) {
                    elem.setRectPos(new Vector2((((Constant.WIDTH / 8) + (j * Constant.WIDTH / 4)) - (2 * Constant.WIDTH)) / 1000, 0 - (Constant.HEIGHT + Constant.RECT_HEIGHT + Constant.HEIGHT / 2) / 1000));
                    j++;
                }
            }
        }
    }

}
