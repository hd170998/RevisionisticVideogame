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


    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }


    public Vaca(float x, float y, boolean orientation) {


        TextureRegion region = new TextureRegion(new Texture("ForestStuff/WalkingMeleeX.png"));
        TextureRegion regionY = new TextureRegion(new Texture("ForestStuff/WalkingMeleeY.png"));
        TextureRegion attackRegion = new TextureRegion(new Texture("ForestStuff/AttackMeleeX.png"));
        TextureRegion attackRegionY = new TextureRegion(new Texture("ForestStuff/AttackMeleeY.png"));
        setSize(width, height);

        TextureRegion[][] texturaVaca = region.split(width, height);
        TextureRegion[][] texturaAtaque = attackRegion.split(288, 160);
        TextureRegion[][] texturaAtaqueY = attackRegionY.split(96, 224);
        TextureRegion[][] texturaVacaY = regionY.split(96, 224);

        if(orientation){
            sprite = new Sprite(texturaVaca[0][0]);
            setX(x);
            setY(y);
            sprite.setPosition(x, y);
            walkingAnimation = new Animation(3f, texturaVaca[0][2], texturaVaca[0][1], texturaVaca[0][0] );

            attackingAnimation = new Animation(2f, texturaAtaque[0][2], texturaAtaque[0][1], texturaAtaque[0][0]);
            boundsVaca = new Rectangle(x, y ,240, 80);

        } else {
            sprite = new Sprite(texturaVacaY[0][0]);
            setX(x);
            setY(y);
            sprite.setPosition(x, y);
            walkingAnimation = new Animation(3f, texturaVacaY[0][5], texturaVacaY[0][4], texturaVacaY[0][3], texturaVacaY[0][2], texturaVacaY[0][1], texturaVacaY[0][0] );
            attackingAnimation = new Animation(2f, texturaAtaqueY[0][7], texturaAtaqueY[0][6], texturaAtaqueY[0][5], texturaAtaqueY[0][4], texturaAtaqueY[0][3], texturaAtaqueY[0][2], texturaAtaqueY[0][1], texturaAtaqueY[0][0]);
            boundsVaca = new Rectangle(x, y ,90, 160);

        }



       //currentState = previousState = Vaca.State.WALKING;



    }




    @Override
    public void update() {
        //x += 1;
        //boundsVaca.setPosition(x, y);
        sprite.setPosition(x, y);
        sprite.flip(true, false);


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
