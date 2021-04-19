package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrowTest {

    @Test
    void render() {
        Arrow arrow1 = new Arrow(new Vector2(0,0), new Vector2(0,0), Constant.Direzioni.BOTTOMLEFT);
        Arrow arrow2 = new Arrow(new Vector2(0,0), new Vector2(0,0), Constant.Direzioni.TOPLEFT);
        Arrow arrow3 = new Arrow(new Vector2(0,0), new Vector2(0,0), Constant.Direzioni.BOTTOMRIGHT);
        Arrow arrow4 = new Arrow(new Vector2(0,0), new Vector2(0,0), Constant.Direzioni.TOPRIGHT);

        assertEquals(arrow1.render(new SpriteBatch()), Constant.Direzioni.BOTTOMLEFT);
        assertEquals(arrow2.render(new SpriteBatch()), Constant.Direzioni.TOPLEFT);
        assertEquals(arrow3.render(new SpriteBatch()), Constant.Direzioni.BOTTOMRIGHT);
        assertEquals(arrow4.render(new SpriteBatch()), Constant.Direzioni.TOPRIGHT);

    }
}