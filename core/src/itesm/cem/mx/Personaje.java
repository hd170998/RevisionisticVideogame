package itesm.cem.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Personaje extends Objeto{
    private Animation animacion;
    private float timerAnimacion;
    private float x, y;   // coordenadas
    EstadoMovimento estadoMover = EstadoMovimento.QUIETO;
    private static final float VX = 240;  // Velocidad en x, [pixeles/segundo]
<<<<<<< HEAD
    public enum EstadoMovimento {
        QUIETO,
        DERECHA,
        IZQUIERDA
=======
    private static final float VY = 240;
    public enum EstadoMovimento {
        QUIETO,
        DERECHA,
        IZQUIERDA,
        ARRIBA,
        ABAJO
>>>>>>> 6d7c1fba55bd837b1ee7c6c2109b50a4f782260e
    }
    public Personaje(Texture textura) {
        // Crea una region
        TextureRegion region = new TextureRegion(textura);
        // Divide la región en frames de 32x64
<<<<<<< HEAD
        TextureRegion[][] texturaPersonaje = region.split(32,64);
=======
        TextureRegion[][] texturaPersonaje = region.split(64,128);
>>>>>>> 6d7c1fba55bd837b1ee7c6c2109b50a4f782260e
        animacion = new Animation(0.15f,texturaPersonaje[0][3],texturaPersonaje[0][2],texturaPersonaje[0][1]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
        // Quieto
        sprite = new Sprite(texturaPersonaje[0][0]);
<<<<<<< HEAD
        sprite.setPosition(0,64);
        x = 0;
        y = 64;
=======
        sprite.setPosition(64,1250);
        x = 64;
        y = 1250;
>>>>>>> 6d7c1fba55bd837b1ee7c6c2109b50a4f782260e
    }
    public void render(SpriteBatch batch) {
        if (estadoMover==EstadoMovimento.QUIETO) {
            //batch.draw(marioQuieto.getTexture(),x,y);
            sprite.draw(batch);
        } else {
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.IZQUIERDA) {
                region.flip(!region.isFlipX(), false);
            } else if (estadoMover == EstadoMovimento.DERECHA) {
                region.flip(region.isFlipX(), false);
            }
            batch.draw(region, x, y);
        }
    }
    public void actualizar(TiledMap mapa) {
        // Verificar si se puede mover (no hay obstáculos, por ahora tubos verdes)
        switch (estadoMover) {
<<<<<<< HEAD
            case DERECHA:
                if (puedeMover(VX*Gdx.graphics.getDeltaTime(),mapa)) {
                    mover(VX * Gdx.graphics.getDeltaTime());
                }
                break;
            case IZQUIERDA:
                mover(-VX*Gdx.graphics.getDeltaTime());
=======
            case ABAJO:
                if (puedeMover(VY*Gdx.graphics.getDeltaTime(),mapa)){
                    moverY(-VY*Gdx.graphics.getDeltaTime());
                }
                break;
            case ARRIBA:
                if (puedeMover(VY*Gdx.graphics.getDeltaTime(),mapa)){
                    moverY((VX*Gdx.graphics.getDeltaTime()));
                }
                break;
            case DERECHA:
                if (puedeMover(VX*Gdx.graphics.getDeltaTime(),mapa)) {
                    moverX(VX * Gdx.graphics.getDeltaTime());
                }
                break;
            case IZQUIERDA:
                moverX(-VX*Gdx.graphics.getDeltaTime());
>>>>>>> 6d7c1fba55bd837b1ee7c6c2109b50a4f782260e
                break;
        }
    }

    private boolean puedeMover(float dx, TiledMap mapa) {
        int cx = (int)(getX()+32)/32;
        int cy = (int)(getY())/32;
        // Obtener la celda en x,y
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(0);
        TiledMapTileLayer.Cell celda = capa.getCell(cx,cy);
<<<<<<< HEAD
        Object tipo = celda.getTile().getProperties().get("tipo");
=======
        Object tipo = celda.getTile().getProperties().get("Tipo");
>>>>>>> 6d7c1fba55bd837b1ee7c6c2109b50a4f782260e
        if (!"Solido".equals(tipo)) {
            // No es obstáculo, puede pasar
            return true;
        }
        return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        sprite.setPosition(x,y);
    }

    public void setY(float y) {
        this.y = y;
        sprite.setPosition(x,y);
    }

<<<<<<< HEAD
    public void mover(float dx) {
        x += dx;
        sprite.setPosition(x,y);
    }
=======
    public void moverX(float dx) {
        x += dx;
        sprite.setPosition(x,y);
    }
    public void moverY(float dy){
        y += dy;
        sprite.setPosition(x,y);
    }
>>>>>>> 6d7c1fba55bd837b1ee7c6c2109b50a4f782260e

    public void setEstadoMover(EstadoMovimento estadoMover) {
        this.estadoMover = estadoMover;
    }

}
