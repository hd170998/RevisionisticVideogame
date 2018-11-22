package itesm.cem.revisionistic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Vaca extends Enemigo {
    private  Animation attackingAnimation;

    public enum State {WALKING, ATTACKING}
    public Vaca.State currentState;
    public Vaca.State previousState;
    private float stateTime;
    public Animation walkingAnimation;
    private Array<TextureRegion> frames;
    Personaje.EstadoMovimento estadoMover = Personaje.EstadoMovimento.QUIETO;
    private Array<TextureRegion> attackVaca;
    public boolean state;
    public Rectangle boundsVaca;
    public static final int height = 160;
    public static final int width = 288;
    private Sprite sprite;
    float x;
    float y;

    public Rectangle getBounds(){
        return boundsVaca;
    }
    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public Vaca(float x, float y, float recHeight, float recWidth) {


        TextureRegion region = new TextureRegion(new Texture("ForestStuff/WalkingMeleeX.png"));
        TextureRegion attackRegion = new TextureRegion(new Texture("ForestStuff/AttackMeleeX.png"));
        setSize(width, height);

        TextureRegion[][] texturaVaca = region.split(width, height);
        TextureRegion[][] texturaAtaque = region.split(288, 160);


        sprite = new Sprite(texturaVaca[0][0]);
        //x = 100;
        //y = 1250;
        setX(x);
        setY(y);
        sprite.setPosition(x, y);
        walkingAnimation = new Animation(3f, texturaVaca[0][2], texturaVaca[0][1], texturaVaca[0][0] );

        velocity = new Vector2(0, 0);
        attackingAnimation = new Animation(2f, texturaAtaque[0][2], texturaAtaque[0][1], texturaAtaque[0][0]);

       //currentState = previousState = Vaca.State.WALKING;

        boundsVaca = new Rectangle(x, y,recWidth, recHeight);
    }




    @Override
    public void update() {
        int i = 0;
        while(i<=10){
            i++;
            x += 1;

            sprite.setPosition(x, y);
            boundsVaca.setPosition(x, y);
        }
        while(i > 10 && i <= 20){
            x -= 1;
            i++;
            sprite.setPosition(x, y);
            boundsVaca.setPosition(x, y);
        }

    }



    @Override
    public void hitOnHead() {
        sprite.getTexture().dispose();
        boundsVaca.setPosition(-1000,-1000);
        state = true;

    }

    @Override
    public void hitByEnemy(Enemigo enemigo) {

    }


}
