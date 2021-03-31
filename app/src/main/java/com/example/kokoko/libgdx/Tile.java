package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;

public class Tile extends Sprite {

    private Texture t_on,t_off;
    public Vector2 tileMapPos;
    public Vector2 worldPos;
    public boolean on;

    
    public Tile(Vector2 tileMapPos, Vector2 worldPos, boolean on) {
        this.tileMapPos = tileMapPos;
        this.worldPos   = worldPos;
        this.t_off      = new Texture("off.png");
        this.t_on       = new Texture("on.png");
        this.on         = on;
    }

    public void render(SpriteBatch batch){
        if(on)
            batch.draw(t_on,worldPos.x, worldPos.y,Constant.TILE_WIDHT,Constant.TILE_HEIGHT);
        else
            batch.draw(t_off,worldPos.x, worldPos.y,Constant.TILE_WIDHT,Constant.TILE_HEIGHT);
    }
}
