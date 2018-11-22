package itesm.cem.revisionistic;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Salida {
    public Rectangle salidaBounds;
    private int width = 128;
    private int height = 32;
    Sprite sprite;


    public Salida(float x, float y){
        TextureRegion region = new TextureRegion(new Texture("ForestStuff/EXITSprite.png"));
        sprite = new Sprite(region);
        sprite.setPosition(x, y);
        salidaBounds = new Rectangle(x, y, width, height);

    }

}
