package itesm.cem.revisionistic;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Vaca extends Enemigo {
    private Animation animacionX;
    private Animation animacionY;
    private float x,y;
    public static final int height = 160;
    public static final int width = 288;
    private Rectangle boundsVaca;
    private Vector2 position;
    public boolean state = false;


    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void update() {

    }

    @Override
    public void setSize(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX( int newX){
        this.x = newX;
    }

    public void setY( int newY){
        this.x = newY;
    }

    public Vaca(int x, int y){


        TextureRegion region = new TextureRegion(new Texture("ForestStuff/WalkingMeleeX.png"));

        TextureRegion[][] texturaVaca = region.split(width,height);
        animacionX = new Animation(0.15f,texturaVaca[0][2],texturaVaca[0][1]);

        //animacionX.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion regionYup= new TextureRegion(new Texture("ForestStuff/WalkingMeleeY.png"));
        TextureRegion [][] texturaVacaY = regionYup.split(width,height);
        //animacionY = new Animation(0.15f, texturaVacaY[0][2], texturaVacaY[0][1], texturaVacaY[0][0]);
        //animacionY.setPlayMode(Animation.PlayMode.LOOP);

        sprite = new Sprite(texturaVaca[0][0]);
        //x = 100;
        //y = 1250;
        sprite.setPosition(x,y);

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        boundsVaca = new Rectangle(x, y, width/2, height /2);

    }

    public boolean collides(Rectangle jugador){
        return jugador.overlaps(boundsVaca);
    }

    public void destroy(){
        sprite.getTexture().dispose();
        state = true;
    }


}
