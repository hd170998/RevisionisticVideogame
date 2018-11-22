package itesm.cem.revisionistic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Slime extends Enemigo{

    private final Animation atackingAnimation;

    public enum State {WALKING, ATTACKING}
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation walkingAnimation;
    private Array<TextureRegion> frames;
    private Array<TextureRegion> attackSlime;
    private boolean setToDestroy;
    private boolean destroyed;

    public Slime( float x, float y){

        frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(new Texture("enemies/GreenSlimeWalkingX.png"), i * 320,128));
        }
        attackSlime = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(new Texture("enemies/GreenSlimeAttackX.png"), i * 320,128));
        }
        walkingAnimation = new Animation(0.2f, frames);
        atackingAnimation = new Animation(0.3f, attackSlime);
        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 320, 128);
    }

    @Override
    protected void defineEnemy() {

    }

    public TextureRegion getFrame(float dt) {

        TextureRegion region;

        switch(currentState){
            case WALKING:
            default:
                region = (TextureRegion) walkingAnimation.getKeyFrame(stateTime, true);
                break;
            case ATTACKING:
                region = (TextureRegion) atackingAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if(velocity.x > 0 && region.isFlipX() == false){
            region.flip(true, false);

        }
        if(velocity.x < 0 && region.isFlipX() == true){
            region.flip(true, false);

        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTime = currentState == previousState ? stateTime + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;


    }

    @Override
    public void update() {


    }

    @Override
    public void hitOnHead() {
        if (currentState != State.WALKING){
            currentState = State.ATTACKING;
        }

    }

    @Override
    public void hitByEnemy(Enemigo enemigo) {

    }


}
