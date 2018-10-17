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
    private Animation animacionX;
    private Animation animacionY;
    private float timerAnimacion;
    private float x, y;   // coordenadas
    EstadoMovimento estadoMover = EstadoMovimento.QUIETO;
    private static float VX;  // Velocidad en x, [pixeles/segundo]
    private static float VY;
    public static final float SPEED = 3;
    public enum EstadoMovimento {
        QUIETO,
        DERECHA,
        IZQUIERDA,
        ARRIBA,
        ABAJO
    }
    public Personaje(Texture textura) {
        // Crea una region
        TextureRegion region = new TextureRegion(textura);
        // Divide la región en frames de 32x64
        TextureRegion[][] texturaPersonaje = region.split(64,128);
        animacionX = new Animation(0.15f,texturaPersonaje[0][2],texturaPersonaje[0][1]);
        animacionX.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion regionYup= new TextureRegion(new Texture("1V4N_YaxisUp.png"));
        TextureRegion [][] texturaPersonajeYup = regionYup.split(64,128);
        animacionY = new Animation(0.15f, texturaPersonajeYup[0][2], texturaPersonajeYup[0][1], texturaPersonajeYup[0][0]);
        animacionY.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
        // Quieto
        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(64,1250);
        x = 64;
        y = 1250;
    }
    public void render(SpriteBatch batch) {
        if (estadoMover==EstadoMovimento.QUIETO) {
            //batch.draw(marioQuieto.getTexture(),x,y);
            sprite.draw(batch);
        } else if (estadoMover== EstadoMovimento.DERECHA || estadoMover==EstadoMovimento.IZQUIERDA){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) animacionX.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.IZQUIERDA) {
                region.flip(!region.isFlipX(), false);
            } else if (estadoMover == EstadoMovimento.DERECHA) {
                region.flip(region.isFlipX(), false);
            }
            batch.draw(region, x, y);
        }else if (estadoMover== EstadoMovimento.ARRIBA){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionUP = (TextureRegion) animacionY.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.ARRIBA) {
                regionUP.flip(false, regionUP.isFlipY());

            }
        }
    }
    public void actualizar(TiledMap mapa) {
        // Verificar si se puede mover (no hay obstáculos, por ahora tubos verdes)
        switch (estadoMover) {
            case ABAJO:
                if (puedeMoverY(-1,mapa)){
                    mover(VX*SPEED, VY*SPEED);
                }
                break;
            case ARRIBA:
                if (puedeMoverY(1,mapa)){
                    mover(VX*SPEED, VY*SPEED);
                }
                break;
            case DERECHA:
                if (puedeMoverX(1,mapa)) {
                    mover(VX*SPEED, VX*SPEED);
                }
                break;
            case IZQUIERDA:
                if (puedeMoverX(-1,mapa)) {
                    mover(VX*SPEED, VY*SPEED);
                }
                break;
        }
    }

    private boolean puedeMoverY(float dy, TiledMap mapa) {
        int cx = (int)(getX())/32;
        int cy = (int)(getY()+(dy*32))/32;
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(1);
        TiledMapTileLayer.Cell celda = capa.getCell(cx,cy);
        // Obtener la celda en x,y
        if (celda!=null){
            Object material = celda.getTile().getProperties().get("Material");
            Gdx.app.log("tipo",material+"");
            if (!"Solido".equals(material)) {
                // No es obstáculo, puede pasar
                return true;
            }
            else{
                return false;
            }
        }
        return true;

    }

    private boolean puedeMoverX(float dx, TiledMap mapa) {
        int cx = (int)(getX()+(dx*32))/32;
        int cy = (int)(getY())/32;
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(1);
        TiledMapTileLayer.Cell celda = capa.getCell(cx,cy);
        // Obtener la celda en x,y
        if (celda!=null){
            Object material = celda.getTile().getProperties().get("Material");
            Gdx.app.log("tipo",material+"");
            if (!"Solido".equals(material)) {
                // No es obstáculo, puede pasar
                return true;
            }
            else{
                return false;
            }
        }
        return true;
    }

    public float getX() {return x;}

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

    public void mover(float dx, float dy) {
        x += dx;
        y += dy;
        sprite.setPosition(x, y);
    }
    public void setVx(float VX) {
        this.VX = VX;
    }

    public void setVy(float VY) {
        this.VY = VY;
    }
    public void setEstadoMover(EstadoMovimento estadoMover) {
        this.estadoMover = estadoMover;
    }

}
