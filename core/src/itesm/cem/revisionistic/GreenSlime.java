package itesm.cem.revisionistic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GreenSlime extends Enemigo{

    public enum State {WALKING, ATTACKING}
    public State currentState;
    public State previousState;
    private float stateTime;
    public Animation walkingAnimation;
    public Animation attackingAnimation;
    private Array<TextureRegion> frames;
    private Array<TextureRegion> attackSlime;
    private boolean setToDestroy;
    private boolean destroyed;
    private Sprite sprite;

    public GreenSlime(float x, float y){

        TextureRegion region = new TextureRegion(new Texture("enemies/GreenSlimeWalkingX.png"));
        TextureRegion attackRegion = new TextureRegion(new Texture("ForestStuff/GreenSlimeAttackX.png"));
        setSize(320, 128);

        TextureRegion[][] texturaSlime = region.split(320, 128);
        TextureRegion[][] texturaAtaque = region.split(320, 128);


        sprite = new Sprite(texturaSlime[0][0]);
        //x = 100;
        //y = 1250;
        setX(x);
        setY(y);
        sprite.setPosition(x, y);
        walkingAnimation = new Animation(3f, texturaSlime[0][2], texturaSlime[0][1], texturaSlime[0][0] );

        velocity = new Vector2(0, 0);
        attackingAnimation = new Animation(2f, texturaAtaque[0][2], texturaAtaque[0][1], texturaAtaque[0][0]);

        //currentState = previousState = Vaca.State.WALKING;

        setBounds(getX(), getY(), 216, 128);
    }



    @Override
    public void update() {


    }

    @Override
    public void hitOnHead() {
        sprite.getTexture().dispose();

    }

    @Override
    public void hitByEnemy(Enemigo enemigo) {

    }


}
