package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.Screen.GameScreen;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class Tilemap {

    //base mappa
    public LinkedList<Tile> base;
    //oggetti mappa
    //public LinkedList<Tile> objects;
    private Texture potenziamento;
    //map
    private String[][] map;
    //map name
    private static String map_prefix="lvl",map_filetype=".txt";
    //
    private static int map_no;
    // gameClass
    private static GameClass gameClass;
    // punteggio
    public static int punteggioTotale = 0;


    public Tilemap(GameClass gameClass){
        /*potenziamento = new Texture("");*/
        this.gameClass = gameClass;
        base    = new LinkedList<Tile>();
        //objects = new LinkedList<Tile>();
        map_no = gameClass.getnLvl() +1;
        punteggioTotale = 0;
        map = new String[10][10];
        try {
            fillMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void render(SpriteBatch batch){
        for(Tile t : base){
            t.render(batch);
        }

        /*for(Tile t : objects){
            t.render(batch);
        }*/
    }

    public void fillMap() throws IOException{
        InputStream is = gameClass.as.open(mapdir());
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);

        String s  = new String(buffer);
        int count_row = 0;
        int count_col = 0;

        String[] temp = s.split("\n");

        count_col = (temp[0].length()+1)/2;
        for(int i = 0; i < temp.length ; i++){
            map[count_row] = temp[count_row].split(" ");
            count_row++;
        }
        is.close();

        map[0][0] = "1"; // per mettere il player
        for(int row = count_row-1; row >= 0; row--){
            for(int col = count_col-1; col >= 0; col--){
                //serve per la visuale isometrica
                float x = (row - col) * (Constant.TILE_SPOS_INIT_X);
                float y = (col + row) * (Constant.TILE_SPOS_INIT_Y);

                if(map[row][col].contains("1") && (GameScreen.getPlayer().getPos().x == row && GameScreen.getPlayer().getPos().y == col)){ //TODO cambiare il metodo di punteggio
                    base.add(new Tile(new Vector2(row,col), new Vector2(x,y), true));
                    punteggioTotale = punteggioTotale + Constant.PUNTEGGIO_PER_TILE;
                }else if (map[row][col].contains("1")  && ((GameScreen.getPlayer().getPos().x != row || GameScreen.getPlayer().getPos().y != col))){
                    base.add(new Tile(new Vector2(row,col), new Vector2(x,y), false));
                    punteggioTotale = punteggioTotale + Constant.PUNTEGGIO_PER_TILE;
                }

            }
        }
    }

    private static String mapdir(){
        return Tilemap.map_prefix+Tilemap.map_no+Tilemap.map_filetype;
    }

    public static void mapUpdate(int nextLevel){
        Tilemap.map_no=(new Integer(nextLevel));
    }


}

