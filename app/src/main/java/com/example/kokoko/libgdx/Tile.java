package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;

/** Classe per la creazione del singolo tile */
public class Tile extends Sprite {
    //public
    public Vector2 tileMapPos;
    public Vector2 worldPos;
    public boolean onBoolean;
    //private
    private Texture tileOn;
    private Texture tileOff;

    public Tile(Vector2 tileMapPos, Vector2 worldPos, boolean onBoolean) {
        this.tileMapPos = tileMapPos;
        this.worldPos = worldPos;
        this.tileOff = new Texture("off.png");
        this.tileOn = new Texture("on.png");
        this.onBoolean = onBoolean;
    }

    public void render(SpriteBatch batch) {
        if (onBoolean) {
            batch.draw(tileOn, worldPos.x, worldPos.y, Constant.TILE_WIDHT, Constant.TILE_HEIGHT);
        }
        else {
            batch.draw(tileOff, worldPos.x, worldPos.y, Constant.TILE_WIDHT, Constant.TILE_HEIGHT);
        }
    }
}
