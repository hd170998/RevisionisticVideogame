package mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class Personaje extends Objeto {
    Rectangle ivRectangle;
    private Animation animacionX;
    private Animation animacionY;
    private Animation animacionYD;
    private Animation animacionXA;
    private float timerAnimacion;
    private int width, height;
    private float x, y;   // coordenadas
    EstadoMovimento estadoMover = EstadoMovimento.QUIETO;
    private static float VX;  // Velocidad en x, [pixeles/segundo]
    private static float VY;
    public static final float SPEED = 5;
    public int life;
    public int documents;
    private Vector3 position;
    private Rectangle jugBounds;
    private Sound saw;

    public enum EstadoMovimento {
        QUIETO,
        DERECHA,
        IZQUIERDA,
        ARRIBA,
        ABAJO,
        ATAQUEX
    }

    public void setSize(int x, int y) {
        this.width = x;
        this.height = y;
    }

    public Personaje(Texture textura) {

        // Crea una region
        TextureRegion region = new TextureRegion(textura);
        setSize(textura.getWidth(), textura.getHeight());
        // Divide la región en frames de 32x64
        TextureRegion[][] texturaPersonaje = region.split(64,128);
        animacionX = new Animation(0.15f,texturaPersonaje[0][2],texturaPersonaje[0][1]);
        animacionX.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion regionYup= new TextureRegion(new Texture("1V4N_YaxisUp.png"));
        TextureRegion [][] texturaPersonajeYup = regionYup.split(64,128);
        animacionY = new Animation(0.15f, texturaPersonajeYup[0][2], texturaPersonajeYup[0][1], texturaPersonajeYup[0][0]);
        animacionY.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion regionDown = new TextureRegion(new Texture("1V4N_YaxisDown.png"));
        TextureRegion [][] texturaPersonajeDown = regionDown.split(64,128);
        animacionYD = new Animation(0.15f,texturaPersonajeDown[0][2], texturaPersonajeDown[0][1], texturaPersonajeDown[0][0]);
        animacionYD.setPlayMode((Animation.PlayMode.LOOP));
        TextureRegion ataquex = new TextureRegion(new Texture("Saw/SawSword_XaxisSingle.png"));
        TextureRegion[][] regionesAtaqueX = ataquex.split(126,134);
        animacionXA = new Animation(0.15f, regionesAtaqueX[0][1],regionesAtaqueX[0][0]);
        animacionXA.setPlayMode((Animation.PlayMode.LOOP_PINGPONG));
        timerAnimacion = 0;
        // Quieto
        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(64,1250);
        x = 64;
        y = 1250;
        position = new Vector3(x,y,0);
        jugBounds = new Rectangle(x, y ,50, 100);
        AssetManager manager = new AssetManager();
        manager.load("audio/saw-audio.mp3",Sound.class);
        manager.finishLoading();
        saw = manager.get("audio/saw-audio.mp3",Sound.class);
    }
    public void render(SpriteBatch batch) {

        if (estadoMover== EstadoMovimento.QUIETO) {
            //batch.draw(marioQuieto.getTexture(),x,y);

            sprite.draw(batch);
        } else if (estadoMover== EstadoMovimento.DERECHA || estadoMover== EstadoMovimento.IZQUIERDA){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) animacionX.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.IZQUIERDA) {
                region.flip(!region.isFlipX(), false);
            } else if (estadoMover == EstadoMovimento.DERECHA) {
                region.flip(region.isFlipX(), false);
            }

            batch.draw(region, x, y);
            jugBounds.setPosition(x,y);


        }else if (estadoMover== EstadoMovimento.ARRIBA){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionUP = (TextureRegion) animacionY.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.ARRIBA) {
                regionUP.flip(false, regionUP.isFlipY());
            }


            batch.draw(regionUP, x, y);
            jugBounds.setPosition(x,y);

        }else if (estadoMover == EstadoMovimento.ABAJO){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionDown = (TextureRegion) animacionYD.getKeyFrame(timerAnimacion);

            sprite.setPosition(x,y);
            batch.draw(regionDown, x, y);
            jugBounds.setPosition(x,y);

        }else if (estadoMover == EstadoMovimento.ATAQUEX){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionAtaque = (TextureRegion) animacionXA.getKeyFrame(timerAnimacion);

            batch.draw(regionAtaque, x, y);
            jugBounds.setPosition(x,y);

        }
    }
    public void actualizar(TiledMap mapa) {

        // Verificar si se puede mover (no hay obstáculos, por ahora tubos verdes)
        switch (estadoMover) {
            case ABAJO:
                if (puedeMover(0,-1,mapa)){
                    mover(VX*SPEED, VY*SPEED);
                }
                break;
            case ARRIBA:
                if (puedeMover(0,1,mapa)){
                    mover(VX*SPEED, VY*SPEED);
                }
                break;
            case DERECHA:
                if (puedeMover(1,0,mapa)) {

                    mover(VX*SPEED, VY*SPEED);
                }
                break;
            case IZQUIERDA:
                if (puedeMover(-1,0,mapa)) {
                    mover(VX*SPEED, VY*SPEED);
                }
                break;
        }
    }

    private boolean puedeMover(float dx,float dy, TiledMap mapa) {
        int cx = (int)(getX()+(dx*32))/32;
        int cy = (int)(getY()+(dy*32))/32;
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(1);
        TiledMapTileLayer.Cell celda = capa.getCell(cx,cy);
        // Obtener la celda en x,y
        if (celda!=null){
            Object material = celda.getTile().getProperties().get("Material");
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

    public void setDocuments(int documents) {
        this.documents = documents;
    }

    public int getDocuments() {
        return documents;
    }

    public void setX(int x) {
        this.x = x;
        sprite.setPosition(x,y);
    }

    public void setY(int y) {
        this.y = y;
        sprite.setPosition(x,y);
    }
    public void setLife(int life){
        this.life = life;
    }
    public int getLife(){
        return life;
    }
    public void mover(float dx, float dy) {
        x += dx;
        y += dy;
        if (x > 600){
            setLife(0);

        }
        sprite.setPosition(x, y);
    }
    public void setVx(float VX) {
        this.VX = VX;
    }
    public void addDocumets(int documents){
        int suma = getDocuments() +documents;
        setDocuments(suma);
    }
    public void damage(int damage){
        int vida = getLife()-damage;
        setLife(vida);

    }

    public Rectangle getBounds(){
        return jugBounds;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setVy(float VY) {
        this.VY = VY;
    }
    public void setEstadoMover(EstadoMovimento estadoMover) {
        this.estadoMover = estadoMover;
    }

}