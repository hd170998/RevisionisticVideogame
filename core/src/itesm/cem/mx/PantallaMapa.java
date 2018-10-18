package itesm.cem.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaMapa extends Pantalla {
    private static final float ANCHO_MAPA = 4800;
    private static final float ALTO_MAPA = 2560;
    private final Juego juego;
    private EstadoJuego estado;
    private EscenaPausa escenaPausa;
    private Music music;

    // Mapas
    private TiledMap mapa;      // El mapa
    private OrthogonalTiledMapRenderer renderer;    // Dibuja el mapa
    private Personaje ivan;    // Mario, lo controla el usuario

    // HUD, otra cámara con la imagen fija
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    // HUD con una escena para los botones y componentes
    private Stage escenaHUD;    // Tendrá un Pad virtual para mover al personaje y el botón de Pausa
    public PantallaMapa(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarMapa();
        cargaMusica();
        ivan = new Personaje(new Texture("ForestStuff/1V4N_Xaxis.png"));
        crearHUD();
        // El input lo maneja la escena
        Gdx.input.setInputProcessor(escenaHUD);

    }

    private void cargaMusica() {
        AssetManager manager = new AssetManager();
        manager.load("Scary-Forest.mp3", Music.class);
        manager.finishLoading();
        music = manager.get("Scary-Forest.mp3");
        music.setLooping(true);
        music.play();
    }

    private void crearHUD() {
        // Crea la cámara y la vista
        camaraHUD = new OrthographicCamera(ANCHO, ALTO);
        camaraHUD.position.set(ANCHO/2, ALTO_MAPA/2-50, 0);
        camaraHUD.update();
        vistaHUD = new StretchViewport(ANCHO, ALTO, camaraHUD);
        // Crea el pad
        Skin skin = new Skin(); // Texturas para el pad
        skin.add("fondo", new Texture("padBack.png"));
        skin.add("boton", new Texture("padKnob.png"));
        // Configura la vista del pad
        Touchpad.TouchpadStyle estilo = new Touchpad.TouchpadStyle();
        estilo.background = skin.getDrawable("fondo");
        estilo.knob = skin.getDrawable("boton");
        // Crea el pad
        Touchpad pad = new Touchpad(64,estilo);     // Radio, estilo
        pad.setBounds(16,16,256,256);               // x,y - ancho,alto
        // Comportamiento del pad
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                ivan.setVx(pad.getKnobPercentX());
                ivan.setVy(pad.getKnobPercentY());
                if (pad.getKnobPercentX() > 0.10) { // Más de 20% de desplazamiento DERECHA
                    ivan.setEstadoMover(Personaje.EstadoMovimento.DERECHA);
                } else if ( pad.getKnobPercentX() < -0.10 ) {   // Más de 20% IZQUIERDA
                    ivan.setEstadoMover(Personaje.EstadoMovimento.IZQUIERDA);
                } else if (pad.getKnobPercentY() > 0.10){
                    ivan.setEstadoMover(Personaje.EstadoMovimento.ARRIBA);
                }else if (pad.getKnobPercentY() < -0.10){
                    ivan.setEstadoMover(Personaje.EstadoMovimento.ABAJO);
                }
                else{
                    ivan.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
                }
            }
        });

        pad.setColor(1,1,1,0.7f);   // Transparente
        Drawable regionPausa = new TextureRegionDrawable(new TextureRegion(new Texture("button_pause.png")));
        Drawable regionPausaOP = new TextureRegionDrawable(new TextureRegion( new Texture("button_pause.png")));
        ImageButton btnPausa = new ImageButton(regionPausa,regionPausaOP);
        btnPausa.setPosition(ANCHO-btnPausa.getWidth(),ALTO-btnPausa.getHeight());
        btnPausa.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estado = EstadoJuego.PAUSADO;
            }
        });

        // Crea la escena y agrega el pad
        escenaHUD = new Stage(vistaHUD);    // Escalar con esta vista
        escenaHUD.addActor(btnPausa);
        escenaHUD.addActor(pad);

    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("ForestMap.tmx",TiledMap.class);
        manager.finishLoading(); // Espera
        mapa = manager.get("ForestMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa);
    }

    @Override
    public void render(float delta) {
        ivan.actualizar(mapa);
        actualizarCamara();
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        renderer.setView(camara);
        camara.position.set(ANCHO/2,ALTO_MAPA/2-50,0);
        camara.update();
        renderer.render();
        batch.begin();
        ivan.render(batch);
        batch.end();
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

        // Botón PAUSA
        if (estado==EstadoJuego.PAUSADO) {
            escenaPausa.draw(); // Solo si está pausado muestra la image
        }
    }

    private void actualizarCamara() {
        // Depende de la posición del personaje. Siempre sigue al personaje
        float posX = ivan.getX();
        float posY = ivan.getY();
        // Primera mitad d
        // e la pantalla

        if (posX < ANCHO/2) {
            camara.position.x = ANCHO/2;
        }
        else if (posX > ANCHO_MAPA - ANCHO/2) {   // Última mitad de la pantalla
            camara.position.x = ANCHO_MAPA-ANCHO/2;
        }
        else {// En 'medio' del mapa
            camara.position.x= posX;
        }
        if(posY == 1250){
            camara.position.y = 1250;
        }
        else if (posY > ALTO_MAPA -ALTO/2){
            camara.position.y = ALTO_MAPA -ALTO/2;
        }
        else if (posY < ALTO/2){
            camara.position.y = ALTO/2;
        }
        else{
            camara.position.y= posY;
        }

        camara.update();
    }
    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
        vistaHUD.update(width, height);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        mapa.dispose();
        escenaHUD.dispose();
    }


    private class ProcesadorEntrada implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, Batch batch) {
            super(vista,batch);
            //transparente
            Pixmap pixmap = new Pixmap((int)(ANCHO*0.7f), (int)(ALTO*0.8f), Pixmap.Format.RGBA8888 );
            pixmap.setColor( 1f, 1f, 1f, 0.65f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f*ANCHO, 0.1f*ALTO);
            this.addActor(imgRectangulo);
            //boton back
            Texture BtnBack=new Texture("BackButton01.png");
            TextureRegionDrawable tback= new TextureRegionDrawable(new TextureRegion(BtnBack));
            Texture BtnBackOP = new Texture("BackButton02HOVER.png");
            TextureRegionDrawable tbackop = new TextureRegionDrawable(new TextureRegion(BtnBackOP));
            ImageButton btnBack = new ImageButton(tback,tbackop);
            btnBack.setPosition(ANCHO/2-btnBack.getWidth()/2, ALTO/2);
            btnBack.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al menú
                    juego.setScreen(new PantallaMapa(juego));
                }
            });
            this.addActor(btnBack);

            // boton resume
            Texture BtnResume=new Texture("BackButton01.png");
            TextureRegionDrawable tresume= new TextureRegionDrawable(new TextureRegion(BtnResume));
            Texture BtnresumeOP = new Texture("BackButton02HOVER.png");
            TextureRegionDrawable tresumeop = new TextureRegionDrawable(new TextureRegion(BtnresumeOP));
            ImageButton btnResume = new ImageButton(tresume,tresumeop);
            btnResume.setPosition(ANCHO/2-btnResume.getWidth()/2, ALTO/4);
            btnResume.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(new ProcesadorEntrada()); // No debería crear uno nuevo
                }
            });
            this.addActor(btnResume);
        }
    }
}
