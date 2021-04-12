package com.example.kokoko.libgdx;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

class TilemapTest {

    @Mock
    private Tilemap tileMap = Mockito.mock(Tilemap.class);

    @Test
    void testConstructor() {
        assertNotNull(tileMap);
    }


}