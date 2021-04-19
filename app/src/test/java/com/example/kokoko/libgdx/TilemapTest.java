package com.example.kokoko.libgdx;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

class TilemapTest {


    @Test
    void testConstructor() {
        Tilemap tileMap = Mockito.mock(Tilemap.class);
        assertNotNull(tileMap);
    }

    @Test
    void testFillMap(){
        GameClass gameClass = Mockito.mock(GameClass.class);
        Tilemap  tileMap = new Tilemap(gameClass);
        assertNotNull(tileMap.arrows);
        assertNotNull(tileMap.base);
    }

}