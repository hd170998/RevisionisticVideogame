package itesm.cem.mx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


public class Enemigo extends Objeto {
    private Animation animation;
    private float timerAnimation;
    private float x, y;
    public Enemigo(){
        TextureRegion region= new TextureRegion(new Texture("WalkingMeleeX.png"));
        TextureRegion[][] texturaMelee = region.split(224,160);
    }

}
