package itesm.cem.revisionistic;

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
    private  Animation atacadoC;
    private Animation animacionX;
    private Animation animacionY;
    private Animation animacionYD;
    private Animation animacionXA;
    private Animation animacionYAU;
    private Animation animacionYAD;
    private Animation atacadoA;
    private Animation atacadoB;
    private Animation atacadoIX;
    private Animation atacadoDe;
    private float timerAnimacion;
    private int width, height;
    private float x, y;   // coordenadas
    EstadoMovimento estadoMover = EstadoMovimento.QUIETO;
    private static float VX;  // Velocidad en x, [pixeles/segundo]
    private static float VY;
    public static final float SPEED = 5;
    public int life;
    public int documents;
    public String estado = new String();
    private Rectangle jugBounds;
    public String movimiento = new String();

    public Rectangle getRectangle() {
        return jugBounds;
    }


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
        TextureRegion [][] texturaPersonajeYup = regionYup.split(64,165);
        animacionY = new Animation(0.15f, texturaPersonajeYup[0][2], texturaPersonajeYup[0][1], texturaPersonajeYup[0][0]);
        animacionY.setPlayMode(Animation.PlayMode.LOOP);
        TextureRegion regionDown = new TextureRegion(new Texture("1V4N_YaxisDown.png"));
        TextureRegion [][] texturaPersonajeDown = regionDown.split(64,128);
        animacionYD = new Animation(0.15f,texturaPersonajeDown[0][2], texturaPersonajeDown[0][1], texturaPersonajeDown[0][0]);
        animacionYD.setPlayMode((Animation.PlayMode.LOOP));
        ///////////////////
        TextureRegion ataquex = new TextureRegion(new Texture("Saw/SawSword_XaxisSingle.png"));
        TextureRegion[][] regionesAtaqueX = ataquex.split(126,134);
        animacionXA = new Animation(0.15f, regionesAtaqueX[0][1],regionesAtaqueX[0][0]);
        animacionXA.setPlayMode((Animation.PlayMode.LOOP));
        ////////////
        TextureRegion ataqueYU = new TextureRegion(new Texture("Saw/SawSword_YUp.png"));
        TextureRegion [][] regionesYAU = ataqueYU.split(110,165);
        animacionYAU = new Animation(0.15f,regionesYAU[0][1], regionesYAU[0][0]);
        animacionYAU.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
        ////////////
        TextureRegion ataqueYD = new TextureRegion(new Texture("Saw/SawSword_Ydown.png"));
        TextureRegion [][] regionesYAD = ataqueYD.split(83,157);
        animacionYAD = new Animation(0.15f,regionesYAD[0][1], regionesYAD[0][0]);
        animacionYAD.setPlayMode(Animation.PlayMode.LOOP);

        ///////Daño animation

        TextureRegion daño = new TextureRegion(new Texture("Damage/Damage_Back.png"));
        TextureRegion [][] regionDaño = daño.split(71,139);
        atacadoA = new Animation(0.15f, regionDaño[0][0], regionDaño[0][1], regionDaño[0][2]);



        TextureRegion dañoFrente = new TextureRegion(new Texture("Damage/Damage_Front.png"));
        TextureRegion [][] frentedaño = dañoFrente.split(61,139);
        atacadoB = new Animation(0.15f, frentedaño[0][0], frentedaño[0][1], frentedaño[0][2]);

        TextureRegion dañoLado = new TextureRegion(new Texture("Damage/Damage_Side.png"));
        TextureRegion [][] regionLados = dañoLado.split(75,120);
        atacadoC = new Animation(0.15f, regionLados[0][0], regionLados[0][1], regionLados[0][2]);





        timerAnimacion = 0;
        ////
        // Quie
        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(64,1250);
        x = 64;
        y = 1250;

        AssetManager manager = new AssetManager();
        manager.load("audio/saw-audio.mp3",Sound.class);
        manager.finishLoading();
    }
    public TextureRegion getAnimation(){
        TextureRegion regionQ = new TextureRegion( new Texture("1V4N_Xaxis.png"));
        // Divide la región en frames de 32x64
        TextureRegion[][] texturaPersonaje = regionQ.split(64,128);
        if (estadoMover==EstadoMovimento.QUIETO) {
            //batch.draw(marioQuieto.getTexture(),x,y);
            movimiento = "QUIETO";
            return texturaPersonaje[0][0];
        }
        else if (estadoMover== EstadoMovimento.DERECHA || estadoMover==EstadoMovimento.IZQUIERDA){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) animacionX.getKeyFrame(timerAnimacion);
            if (estadoMover == EstadoMovimento.IZQUIERDA) {
                region.flip(!region.isFlipX(), false);
                movimiento = "IZQUIERDA";
            } else if (estadoMover == EstadoMovimento.DERECHA) {
                region.flip(region.isFlipX(), false);
                movimiento = "DERECHA";
            }
            return  region;
        }else if (estadoMover== EstadoMovimento.ARRIBA){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionUP = (TextureRegion) animacionY.getKeyFrame(timerAnimacion);
            movimiento = "ARRIBA";
            return regionUP;

        }else if (estadoMover == EstadoMovimento.ABAJO){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionDown = (TextureRegion) animacionYD.getKeyFrame(timerAnimacion);
            movimiento = "ABAJO";
            return regionDown;

        }else if (estadoMover == EstadoMovimento.ATAQUEX || estadoMover== EstadoMovimento.ATAQUEXI){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionAtaqueX = (TextureRegion) animacionXA.getKeyFrame(timerAnimacion);
            movimiento = "ATAQUE";

            if (estadoMover==EstadoMovimento.ATAQUEX) {
                regionAtaqueX.flip(regionAtaqueX.isFlipX(), false);

            }
            else {
                regionAtaqueX.flip(!regionAtaqueX.isFlipX(), false);
            }
            return regionAtaqueX;
        }else if (estadoMover == EstadoMovimento.ATAQUEYUP){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionAtaqueYU = (TextureRegion) animacionYAU.getKeyFrame(timerAnimacion);
            movimiento = "ATAQUE";
            return regionAtaqueYU;
        }else if (estadoMover == EstadoMovimento.ATAQUEYDOWN){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionAtaqueYD = (TextureRegion) animacionYAD.getKeyFrame(timerAnimacion);
            movimiento = "ATAQUE";
            return regionAtaqueYD;
        }

        if(estado == "atacado"){
            timerAnimacion += Gdx.graphics.getDeltaTime();
            TextureRegion regionAtacadoX = (TextureRegion) atacadoB.getKeyFrame(timerAnimacion);
            return regionAtacadoX;

        }
        return texturaPersonaje[0][0];
    }

    public void actualizar(TiledMap mapa) {

        // Verificar si se puede mover (no hay obstáculos, por ahora tubos verdes)
        switch (estadoMover) {
            case ABAJO:
                if (puedeMover(0,-1,mapa)){
                    moverY(-SPEED);
                }
                break;
            case ARRIBA:
                if (puedeMover(0,1,mapa)){
                    moverY(SPEED);
                }
                break;
            case DERECHA:
                if (puedeMover(1,0,mapa)) {

                    moverx(SPEED);
                }
                break;
            case IZQUIERDA:
                if (puedeMover(-1,0,mapa)) {
                    moverx(-SPEED);
                }
                break;
            case ATAQUEYDOWN:
                if (puedeMover(0,-1,mapa)){
                }
        }
    }

    private boolean puedeMover(float dx,float dy, TiledMap mapa) {
        int cx = (int)(getX()+(dx*32))/32;
        int cy = (int)(getY()+(dy*32))/32;
        TiledMapTileLayer capa = (TiledMapTileLayer)mapa.getLayers().get(1);
        TiledMapTileLayer capa2 = (TiledMapTileLayer)mapa.getLayers().get(6);
        TiledMapTileLayer.Cell celda2 = capa2.getCell(cx,cy);
        TiledMapTileLayer.Cell celda = capa.getCell(cx,cy);
        // Obtener la celda en x,y
        if (celda2!=null){
            celda2.setTile(null);
            addDocumets(1);

        }
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
    public void moverx(float dx) {
        x += dx;

        sprite.setPosition(x, y);

    }
    public void moverY(float dy){
        y += dy;
        sprite.setPosition(x,y);

    }

    public void addDocumets(int documents){
        int suma = getDocuments() +documents;
        setDocuments(suma);
    }

    public void damage(int damage){
        int vida = getLife()-damage;
        setLife(vida);

    }


    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setEstadoMover(EstadoMovimento estadoMover) {
        this.estadoMover = estadoMover;
    }

}
