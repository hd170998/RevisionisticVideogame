package itesm.cem.revisionistic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Slime extends Enemigo{



    public boolean state;
    public Animation walkingAnimation;
    public Animation attackingAnimation;
    private Sprite sprite;
    public Rectangle boundsSlime;
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

    public Slime(float recx, float recy, int type){

        TextureRegion greenRegion = new TextureRegion(new Texture("enemies/GreenSlimeWalkingX.png"));
        TextureRegion greenAttackRegion = new TextureRegion(new Texture("enemies/GreenSlimeAttackX.png"));

        TextureRegion blueRegion = new TextureRegion(new Texture("BlueSlimeWalkingX.png"));
        TextureRegion blueAttackRegion = new TextureRegion(new Texture("BlueSlimeAttackX.png"));

        TextureRegion redRegion = new TextureRegion(new Texture("RedSlimeWalkingX.png"));
        TextureRegion redAttackRegion = new TextureRegion(new Texture("RedSlimeAttackX.png"));



        setSize(320, 128);

        TextureRegion[][] texturaGreenSlime = greenRegion.split(320, 128);
        TextureRegion[][] texturaGreenAtaque = greenAttackRegion.split(320, 128);

        TextureRegion[][] texturaBlueSlime = blueRegion.split(320, 128);
        TextureRegion[][] texturaBlueAtaque = blueAttackRegion.split(320, 128);

        TextureRegion[][] texturaRedSlime = redRegion.split(320, 128);
        TextureRegion[][] texturaRedAtaque = redAttackRegion.split(320, 128);


        if(type ==1){
            sprite = new Sprite(texturaGreenSlime[0][0]);

            setX(recx);
            setY(recy);
            sprite.setPosition(recx, recy);
            walkingAnimation = new Animation(3f, texturaGreenSlime[0][3], texturaGreenSlime[0][2], texturaGreenSlime[0][1], texturaGreenSlime[0][0] );
            attackingAnimation = new Animation(2f, texturaGreenAtaque[0][3], texturaGreenAtaque[0][2], texturaGreenAtaque[0][1], texturaGreenAtaque[0][0]);
            boundsSlime = new Rectangle(recx,recy, 150, 100);
        } else if (type==2 ) {
            sprite = new Sprite(texturaBlueSlime[0][0]);

            setX(recx);
            setY(recy);
            sprite.setPosition(recx, recy);
            walkingAnimation = new Animation(3f, texturaBlueSlime[0][3], texturaBlueSlime[0][2], texturaBlueSlime[0][1], texturaBlueSlime[0][0] );
            attackingAnimation = new Animation(2f, texturaBlueAtaque[0][3], texturaBlueAtaque[0][2], texturaBlueAtaque[0][1], texturaBlueAtaque[0][0]);
            boundsSlime = new Rectangle(recx, recy, 150, 100);
        } else if (type==3){
            sprite = new Sprite(texturaRedSlime[0][0]);

            setX(x);
            setY(y);
            sprite.setPosition(x, y);
            walkingAnimation = new Animation<TextureRegion>(3f, texturaRedSlime[0][3], texturaRedSlime[0][2], texturaRedSlime[0][1], texturaRedSlime[0][0] );
            attackingAnimation = new Animation<TextureRegion>(2f, texturaRedAtaque[0][3], texturaRedAtaque[0][2], texturaRedAtaque[0][1], texturaRedAtaque[0][0]);
            boundsSlime = new Rectangle(x, y, 300, 100);
        }


        //currentState = previousState = Vaca.State.WALKING;

    }



    @Override
    public void update() {


    }

    @Override
    public void hitOnHead() {
        sprite.getTexture().dispose();
        boundsSlime.setPosition(-1000,-1000);
        state = true;

    }

    @Override
    public void hitByEnemy(Enemigo enemigo) {

    }


}
