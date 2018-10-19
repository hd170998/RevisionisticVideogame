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
        animation = new Animation(0.15f,texturaMelee[0][2],texturaMelee[0][1], texturaMelee[0][0]);
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

}
