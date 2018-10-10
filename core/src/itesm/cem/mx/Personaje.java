package itesm.cem.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje extends Objeto{
    private Animation animacion;
    private float timerAnimacion;
    private float x, y;   // coordenadas
    EstadoMovimento estadoMover = EstadoMovimento.QUIETO;
    private static final float VX = 240;  // Velocidad en x, [pixeles/segundo]
    public enum EstadoMovimento {
        QUIETO,
        DERECHA,
        IZQUIERDA
    }
    public Personaje(Texture textura) {
        // Crea una region
        TextureRegion region = new TextureRegion(textura);
        // Divide la región en frames de 32x64
        TextureRegion[][] texturaPersonaje = region.split(32,64);
        animacion = new Animation(0.15f,texturaPersonaje[0][3],texturaPersonaje[0][2],texturaPersonaje[0][1]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
        // Quieto
        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(0,64);
        x = 0;
        y = 64;
    }

}
