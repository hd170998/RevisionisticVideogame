package itesm.cem.revisionistic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PantallaMapa extends Pantalla {
    private static final float ANCHO_MAPA = 4800;
    private static final float ALTO_MAPA = 2560;
    private final PantallaInicio pantallaInicio;
    private EstadoJuego estado;
    private EscenaPausa escenaPausa;
    private Music music;
    private Label label;
    private BitmapFont font;

    // Mapas
    private TiledMap mapa;      // El mapa
    private OrthogonalTiledMapRenderer renderer;    // Dibuja el mapa
    private Personaje ivan;    // Mario, lo controla el usuario
    public static Vaca vaca1;
    public static Vaca vaca2;
    public static Vaca vaca3;


    // HUD, otra cámara con la imagen fija
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    // HUD con una escena para los botones y componentes
    private Stage escenaHUD;    // Tendrá un Pad virtual para mover al personaje y el botón de Pausa
    public float width,height;


    public PantallaMapa(PantallaInicio pantallaInicio) { this.pantallaInicio = pantallaInicio;}


    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        cargarMapa();
        cargaMusica();
        ivan = new Personaje(new Texture("1V4N_Xaxis.png"));

        vaca1 = new Vaca(200,1250);


        vaca2 = new Vaca(800,1250);


        vaca3 = new Vaca(400, 1850);


        ivan.setLife(100);
        ivan.setDocuments(0);
        crearHUD();

        // El input lo maneja la escena
        Gdx.input.setInputProcessor(escenaHUD);
    }


    private void cargaMusica() {
        AssetManager manager = new AssetManager();
        manager.load("audio/Forest.mp3", Music.class);
        manager.load("audio/saw-audio.mp3",Sound.class);
        manager.finishLoading();
        music = manager.get("audio/Forest.mp3");
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
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        label= new Label(String.format("%03d",ivan.getLife()),labelStyle);
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
                if (escenaPausa == null){
                    escenaPausa = new EscenaPausa(vistaHUD, batch);
                }
                estado = EstadoJuego.PAUSADO;
                Gdx.input.setInputProcessor(escenaPausa);
                // PASA EL CONTROL A LA ESCENA
            }
        });
        Drawable regionAtaque = new TextureRegionDrawable(new TextureRegion(new Texture("atacar.png")));
        Drawable regionAtaqueOP = new TextureRegionDrawable(new TextureRegion( new Texture("atacar.png")));
        final ImageButton btnAtaque = new ImageButton(regionAtaque,regionAtaqueOP);
        btnAtaque.setPosition(ANCHO-btnAtaque.getWidth(),btnAtaque.getHeight());
        btnAtaque.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(btnAtaque.isChecked()){
                    ivan.setEstadoMover(Personaje.EstadoMovimento.ATAQUEX);
                    btnAtaque.setChecked(true);
                } else {
                    ivan.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
                    btnAtaque.setChecked(false);
                }
            }
        });
        Drawable regionCorazon = new TextureRegionDrawable(new TextureRegion( new Texture("heartSprite.png")));
        Image corazon = new Image(regionCorazon);
        corazon.setPosition(corazon.getWidth()/2, ALTO-corazon.getHeight());
        label.setPosition(corazon.getWidth(),ALTO-label.getHeight());
        // Crea la escena y agrega el pad
        escenaHUD = new Stage(vistaHUD);// Escalar con esta vista
        escenaHUD.addActor(btnPausa);
        escenaHUD.addActor(pad);
        escenaHUD.addActor(btnAtaque);
        escenaHUD.addActor(corazon);
        escenaHUD.addActor(label);

    }

    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("ForestStuff/ForestMap.tmx",TiledMap.class);
        manager.finishLoading(); // Espera
        mapa = manager.get("ForestStuff/ForestMap.tmx");
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

        if(vaca1.state != true){
            vaca1.update();
            vaca1.render(batch);
        }

        if(vaca2.state != true){
            vaca2.update();
            vaca2.render(batch);
        }


        vaca3.update();
        vaca3.render(batch);

        if(vaca1.collides(ivan.getBounds())){
            vaca1.destroy();
        }

        if(vaca2.collides(ivan.getBounds())){
            vaca2.destroy();
        }

        if(vaca3.collides(ivan.getBounds())){
            pantallaInicio.setScreen(new PantallaGameOver(pantallaInicio));
        }

        batch.end();

        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

        // Botón PAUSA
        if (estado == EstadoJuego.PAUSADO){
            escenaPausa.draw();
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
        this.estado = estado.PAUSADO;

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        mapa.dispose();
        escenaHUD.dispose();
        music.dispose();
        //escenaPausa.dispose();
        batch.dispose();

    }
    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vistaHUD, Batch batch) {
            super(vistaHUD,batch);
            //transparente
            Pixmap pixmap = new Pixmap((int)(ANCHO), (int)(ALTO), Pixmap.Format.RGBA8888 );
            pixmap.setColor( 1f, 1f, 1f, 0.65f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0, 0);
            this.addActor(imgRectangulo);
            //base Settings
            Texture baseOpciones = new Texture("Options/SettingsBase.png");
            Image Opcion = new Image(baseOpciones);
            Opcion.setPosition(ANCHO/2-baseOpciones.getWidth()/2, ALTO/2-baseOpciones.getHeight()/2);
            this.addActor(Opcion);
            //boton Musica
            Drawable regionMusic = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonMusic_On.png")));
            Drawable regionMusicOP = new TextureRegionDrawable(new TextureRegion( new Texture("Options/ButtonMusic_Off.png")));
            final ImageButton btnMusic = new ImageButton(regionMusic,regionMusicOP, regionMusicOP);
            btnMusic.setPosition(ANCHO/2-btnMusic.getWidth()/2,5*ALTO/8);
            btnMusic.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (btnMusic.isChecked()){
                        btnMusic.setChecked(true);
                        music.pause();
                    }
                    else {
                        btnMusic.setChecked(false);
                        music.play();
                    }
                }
            });
            this.addActor(btnMusic);
            //boton sound FX

            Drawable regionSound = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonSoundFX_On.png")));
            Drawable regionSoundOP = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonSoundFX_Off.png")));

            ImageButton btnSound = new ImageButton(regionSound,regionSoundOP);
            btnSound.setPosition(ANCHO/2-btnSound.getWidth()/2,4*ALTO/8);
            this.addActor(btnSound);
            //boton resume

            Drawable regionResume = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonResume_Normal.png")));
            Drawable regionResumeOP = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonResume_Click.png")));

            ImageButton btnResume = new ImageButton(regionResume,regionResumeOP);
            btnResume.setPosition(ANCHO/2-btnSound.getWidth()/2,1*ALTO/4);
            btnResume.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaHUD); // No debería crear uno nuevo
                }
            });
            this.addActor(btnResume);
            //boton credits

            Drawable regionCredits = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonCredits_Normal.png")));
            Drawable regionCreditsOP = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonCredits_Click.png")));

            ImageButton  btnCredits = new ImageButton(regionCredits, regionCreditsOP);
            btnCredits.setPosition(ANCHO/2-btnSound.getWidth()/2,1*ALTO/8);
            btnCredits.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pantallaInicio.setScreen (new PantallaCreditos(pantallaInicio));
                }
            });
            this.addActor(btnCredits);

            //boton Menu

            Drawable regionMenu = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonCredits_Normal.png")));
            Drawable regionMenuOP = new TextureRegionDrawable(new TextureRegion(new Texture("Options/ButtonCredits_Click.png")));

            ImageButton  btnMenu = new ImageButton(regionMenu, regionMenuOP);
            btnMenu.setPosition(ANCHO/2-btnSound.getWidth()/2,1*ALTO/8);
            btnMenu.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pantallaInicio.setScreen (new PantallaMenu(pantallaInicio));
                }
            });
            // boton back
            Texture BtnBACK=new Texture("BackButton01.png");
            TextureRegionDrawable tBACK= new TextureRegionDrawable(new TextureRegion(BtnBACK));
            Texture BtnresumeOP = new Texture("BackButton02HOVER.png");
            TextureRegionDrawable tBACKop = new TextureRegionDrawable(new TextureRegion(BtnresumeOP));
            ImageButton btnBACK = new ImageButton(tBACK,tBACKop);
            btnBACK.setPosition(btnBACK.getWidth()/2, ALTO-btnBACK.getHeight());
            btnBACK.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaHUD); // No debería crear uno nuevo
                }
            });
            this.addActor(btnBACK);

        }

    }

}
