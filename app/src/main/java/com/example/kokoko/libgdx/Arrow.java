package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;

/** Classe per la crezione di un arrow */
public class Arrow extends Sprite {

    //public variables
    public Vector2 arrowMapPos;
    public Vector2 worldPos;
    public Constant.Direzioni direzione;

    // private variables
    private Texture arrow;

    public Arrow(Vector2 tileMapPos, Vector2 worldPos, Constant.Direzioni dir) {
        this.arrowMapPos = tileMapPos;
        this.worldPos = worldPos;
        this.direzione = dir;
        this.arrow = new Texture("arrow.png");
    }

    public Constant.Direzioni render(SpriteBatch batch) {
        if (direzione == Constant.Direzioni.BOTTOMRIGHT) {
            batch.draw(arrow, worldPos.x, worldPos.y, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, 0, 0, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, false, false);
            return Constant.Direzioni.BOTTOMRIGHT;
        }
        else if (direzione == Constant.Direzioni.TOPRIGHT) {
            batch.draw(arrow, worldPos.x, worldPos.y, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, 0, 0, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, false, true);
            return Constant.Direzioni.TOPRIGHT;
        }
        else if (direzione == Constant.Direzioni.BOTTOMLEFT) {
            batch.draw(arrow, worldPos.x, worldPos.y, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, 0, 0, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, true, false);
            return Constant.Direzioni.BOTTOMLEFT;
        }
        else {
            batch.draw(arrow, worldPos.x, worldPos.y, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, 0, 0, Constant.ARROW_WIDHT, Constant.ARROW_HEIGHT, true, true);
            return Constant.Direzioni.TOPLEFT;
        }
    }

}
