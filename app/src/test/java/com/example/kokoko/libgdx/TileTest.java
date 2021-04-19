package com.example.kokoko.libgdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TileTest {

    @Test
    void render() {
        Tile tile = new Tile(new Vector2(0,0), new Vector2(0,0),true);
        Tile tile2 = new Tile(new Vector2(0,0), new Vector2(0,0), false);

        Texture texture = new Texture(new FileHandle("off.png"));
        Texture texture2 = new Texture(new FileHandle("on.png"));

        tile.tileOn = texture2;
        tile.tileOff = texture;
        tile2.tileOn = texture2;
        tile2.tileOff = texture;

        when(tile.render(new SpriteBatch())).thenReturn(true);
        when(tile2.render(new SpriteBatch())).thenReturn(false);

    }
    
}