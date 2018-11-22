package itesm.cem.revisionistic;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import itesm.cem.revisionistic.Personaje;


public abstract class Enemigo extends Sprite {
    //protected World world;
    protected PantallaInicio screen;
    public Body b2body;
    public Vector2 velocity;

    public Enemigo( ){
       // this.world = screen.getWorld();

       // setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, -2);


    }

    protected abstract void defineEnemy();
    public abstract void update();
    public abstract void hitOnHead();
    public abstract void hitByEnemy(Enemigo enemigo);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
}


