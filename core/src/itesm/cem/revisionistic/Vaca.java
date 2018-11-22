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
    private Animation walkingAnimation;
    private Array<TextureRegion> frames;
    Personaje.EstadoMovimento estadoMover = Personaje.EstadoMovimento.QUIETO;
    private Array<TextureRegion> attackVaca;
    private float timerAnimacion;
    private boolean setToDestroy;
    private Rectangle boundsVaca;
    private boolean destroyed;
    public static final int height = 160;
    public static final int width = 288;
    public boolean state = false;
    private Sprite sprite;

    public enum EstadoMovimento {
        QUIETO,
        DERECHA,
        IZQUIERDA,
        ARRIBA,
        ABAJO,
        ATAQUEX,
        ATAQUEYUP,
        ATAQUEYDOWN,
        ATAQUEXI
    }



    /*public Vaca(Texture textura){


        TextureRegion region = new TextureRegion(textura);
        setSize(textura.getWidth(), textura.getHeight());

        TextureRegion[][] texturaVaca = region.split(width,height);
        walkingAnimation = new Animation(0.15f, texturaVaca[0][2], texturaVaca[0][1]);

        //animacionX.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion regionYup= new TextureRegion(new Texture("ForestStuff/WalkingMeleeY.png"));
        TextureRegion [][] texturaVacaY = regionYup.split(width,height);
        //animacionY = new Animation(0.15f, texturaVacaY[0][2], texturaVacaY[0][1], texturaVacaY[0][0]);
        //animacionY.setPlayMode(Animation.PlayMode.LOOP);

        sprite = new Sprite(texturaVaca[0][0]);
        //x = 100;
        //y = 1250;
        sprite.setPosition(200, 1250);


        velocity = new Vector2(0, 0);
        //boundsVaca = new Rectangle(x, y, width, height );

        /*frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(new Texture("ForestStuff/WalkingMeleeX.png"), i * 216,160));
        }
        attackVaca = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++){
            frames.add(new TextureRegion(new Texture("ForestStuff/AttackMeleeX.png"), i * 288,160));
        }
        walkingAnimation = new Animation(0.2f, frames);
        attackingAnimation = new Animation(0.3f, attackVaca);
        currentState = previousState = Vaca.State.WALKING;

        setBounds(getX(), getY(), 216, 128);
    }*/

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    protected void defineEnemy() {

    }

    @Override
    public void update(float dt) {

    }

    public TextureRegion getAnimation(){
        TextureRegion regionQ = new TextureRegion( new Texture("ForestStuff/WalkingMeleeX.png"));
        // Divide la región en frames de 32x64
        TextureRegion[][] texturaPersonaje = regionQ.split(288,128);
        if (estadoMover==Personaje.EstadoMovimento.QUIETO) {
            //batch.draw(marioQuieto.getTexture(),x,y);
            return texturaPersonaje[0][0];
        } else if (estadoMover== Personaje.EstadoMovimento.DERECHA || estadoMover==Personaje.EstadoMovimento.IZQUIERDA){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) walkingAnimation.getKeyFrame(timerAnimacion);
            if (estadoMover == Personaje.EstadoMovimento.IZQUIERDA) {
                region.flip(!region.isFlipX(), false);
            } else if (estadoMover == Personaje.EstadoMovimento.DERECHA) {
                region.flip(region.isFlipX(), false);
            }
            return  region;

        }else if (estadoMover == Personaje.EstadoMovimento.ATAQUEX || estadoMover== Personaje.EstadoMovimento.ATAQUEXI){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionAtaqueX = (TextureRegion) attackingAnimation.getKeyFrame(timerAnimacion);

            if (estadoMover==Personaje.EstadoMovimento.ATAQUEX) {
                regionAtaqueX.flip(regionAtaqueX.isFlipX(), false);
            }
            else {
                regionAtaqueX.flip(!regionAtaqueX.isFlipX(), false);
            }
            return regionAtaqueX;
        }
        return texturaPersonaje[0][0];
    }




    public void update() {

    }


    /*public void update(float dt) {
        setRegion(getFrame(dt));
        if(currentState == Vaca.State.ATTACKING && stateTime > 3){
            currentState = Vaca.State.WALKING;
        }

        setPosition(100, 1250);

    }*/

    @Override
    public void hitOnHead(Personaje ivan) {
        if (currentState != Vaca.State.WALKING){
            currentState = Vaca.State.ATTACKING;
        }

    }

    @Override
    public void hitByEnemy(Enemigo enemigo) {

    }



    /*public Vaca(int x, int y){

        TextureRegion region = new TextureRegion(new Texture("ForestStuff/WalkingMeleeX.png"));

        TextureRegion[][] texturaVaca = region.split(width,height);
        walkingAnimation = new Animation(0.15f,texturaVaca[0][2],texturaVaca[0][1]);

        //animacionX.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion regionYup= new TextureRegion(new Texture("ForestStuff/WalkingMeleeY.png"));
        TextureRegion [][] texturaVacaY = regionYup.split(width,height);
        //animacionY = new Animation(0.15f, texturaVacaY[0][2], texturaVacaY[0][1], texturaVacaY[0][0]);
        //animacionY.setPlayMode(Animation.PlayMode.LOOP);

        sprite = new Sprite(texturaVaca[0][0]);
        //x = 100;
        //y = 1250;
        sprite.setPosition(x,y);



        boundsVaca = new Rectangle(x, y, width/2, height /2);

    }*/

    public boolean collides(Rectangle jugador){
        return jugador.overlaps(boundsVaca);
    }

    public void destroy(){
        sprite.getTexture().dispose();
        state = true;
    }



}
