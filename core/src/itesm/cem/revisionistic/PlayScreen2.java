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
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen2 extends Pantalla {
    private static final float ANCHO_MAPA = 2560;
    private static final float ALTO_MAPA = 1280;
    private TiledMap mapa;
    private final PantallaInicio pantallaInicio;
    private TiledMapRenderer tiledMapRenderer;
    private Personaje ivan;
    private MapLayer objectLayer;
    private Label label, labeld;
    private BitmapFont font;
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    private Salida salida;
    private MapObject salidaLayer;
    // HUD con una escena para los botones y componentes
    private Stage escenaHUD;
    private Sound saw;
    private EstadoJuego estado;
    private Music music;
    private EscenaPausa escenaPausa;
    private MapObjects objetos;
    
    private Array<Slime> slimes;
    
    private Rectangle jugBounds;
    private float stateTime;

    public PlayScreen2(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;

    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        ivan = new Personaje(new Texture("1V4N_Xaxis.png"));
        ivan.setX(995);
        ivan.setY(5);
        ivan.setLife(100);
        ivan.setDocuments(0);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        cargarMapa();
        crearHUD();
        cargaMusica();
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
        labeld = new Label(String.format("%01d",ivan.getDocuments()),labelStyle);
        ////PAD
        Drawable Pup = new TextureRegionDrawable(new TextureRegion(new Texture("Pad/PadUp.png")));
        ImageButton btnPup = new ImageButton(Pup);
        btnPup.setPosition(btnPup.getWidth()-10,btnPup.getHeight()*2.5f);
        btnPup.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.ARRIBA);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
            }
        });
        Drawable Pdown = new TextureRegionDrawable(new TextureRegion(new Texture("Pad/PadDown.png")));
        ImageButton btnPdown = new ImageButton(Pdown);
        btnPdown.setPosition(btnPdown.getWidth()-10,Pdown.getTopHeight());
        btnPdown.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.ABAJO);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
            }
        });
        Drawable Pri = new TextureRegionDrawable(new TextureRegion(new Texture("Pad/PadRight.png")));
        ImageButton btnri = new ImageButton(Pri);
        btnri.setPosition(2*btnri.getWidth(),ALTO/6);
        btnri.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.DERECHA);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
            }
        });
        Drawable Pleft = new TextureRegionDrawable(new TextureRegion(new Texture("Pad/PadLeft.png")));
        ImageButton btnleft = new ImageButton(Pleft);
        btnleft.setPosition(Pleft.getLeftWidth(),ALTO/6);
        btnleft.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.IZQUIERDA);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ivan.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
            }
        });
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
        ImageButton btnAtaque = new ImageButton(regionAtaque,regionAtaqueOP);
        btnAtaque.setPosition(ANCHO-btnAtaque.getWidth(),btnAtaque.getHeight()/8);
        btnAtaque.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                saw.play();
                if (ivan.estadoMover==Personaje.EstadoMovimento.DERECHA||ivan.estadoMover==Personaje.EstadoMovimento.QUIETO){
                    ivan.setEstadoMover(Personaje.EstadoMovimento.ATAQUEX);
                }else if (ivan.estadoMover== Personaje.EstadoMovimento.IZQUIERDA){
                    ivan.setEstadoMover(Personaje.EstadoMovimento.ATAQUEXI);
                }
                else if (ivan.estadoMover==Personaje.EstadoMovimento.ARRIBA){
                    ivan.setEstadoMover(Personaje.EstadoMovimento.ATAQUEYUP);

                }
                else if (ivan.estadoMover==Personaje.EstadoMovimento.ABAJO){
                    ivan.setEstadoMover(Personaje.EstadoMovimento.ATAQUEYDOWN);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                saw.stop();
                ivan.setEstadoMover(Personaje.EstadoMovimento.QUIETO);
                //super.touchUp(event, x, y, pointer, button);
            }
        });
        Drawable regionCorazon = new TextureRegionDrawable(new TextureRegion( new Texture("heartSprite.png")));
        Image corazon = new Image(regionCorazon);
        corazon.setPosition(corazon.getWidth()/2, ALTO-corazon.getHeight());
        label.setPosition(corazon.getWidth()+(label.getWidth()/1.5f),ALTO-(label.getHeight()/.8f));
        Drawable regionDocumento = new TextureRegionDrawable(new TextureRegion( new Texture("FileSprite.png")));
        Image documento = new Image(regionDocumento);
        documento.setPosition(ANCHO/2,ALTO-documento.getHeight());
        labeld.setPosition(documento.getWidth()+ANCHO/2+(labeld.getWidth()/1.5f),ALTO-(labeld.getHeight()/.8f));
        // Crea la escena y agrega el pad
        escenaHUD = new Stage(vistaHUD);// Escalar con esta vista
        escenaHUD.addActor(btnPausa);
        escenaHUD.addActor(btnAtaque);
        escenaHUD.addActor(corazon);
        escenaHUD.addActor(label);
        escenaHUD.addActor(documento);
        escenaHUD.addActor(labeld);
        escenaHUD.addActor(btnPup);
        escenaHUD.addActor(btnPdown);
        escenaHUD.addActor(btnleft);
        escenaHUD.addActor(btnri);

    }

    private void actualizarCamara() {
        // Depende de la posición del personaje. Siempre sigue al personaje
        float posX = ivan.getX();
        float posY = ivan.getY();

        // Primera mitad
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


    private void cargarMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("VillageStuff/VillageMap.tmx", TiledMap.class);
        manager.load("audio/saw-audio.mp3", Sound.class);
        manager.finishLoading(); // Espera
        mapa = manager.get("VillageStuff/VillageMap.tmx");
        saw = manager.get("audio/saw-audio.mp3", Sound.class);
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(mapa);
        objectLayer = mapa.getLayers().get("1V4N");
        TextureMapObject IvanO = new TextureMapObject(ivan.getAnimation());
        objectLayer.getObjects().add(IvanO);
        
        slimes = new Array<Slime>();
        for(MapObject object : mapa.getLayers().get("Object Layer 6").getObjects()){

            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            slimes.add(new Slime(rect.x, rect.y,1));



        }
        salidaLayer = mapa.getLayers().get("Object Layer 8").getObjects().get(0);
        Rectangle rect = ((RectangleMapObject) salidaLayer).getRectangle();
        salida = new Salida(rect.x,rect.y);


    }

    private void SetIvanBounds(float x, float y){
        jugBounds = new Rectangle(x, y, 64, 128);
    }

    @Override
    public void render(float delta) {
        ivan.actualizar(mapa);
        actualizarCamara();
        borrarPantalla(0,0,0);
        tiledMapRenderer.setView(camara);
        batch.setProjectionMatrix(camara.combined);
        camara.position.set(ANCHO/2,ALTO_MAPA/2-50,0);
        camara.update();
        tiledMapRenderer.render();

        TextureMapObject character = (TextureMapObject)mapa.getLayers().get("1V4N").getObjects().get(0);
        character.setX(ivan.getX());
        character.setY(ivan.getY());
        character.setTextureRegion(ivan.getAnimation());
        SetIvanBounds(ivan.getX(), ivan.getY());

        batch.begin();
        salida.sprite.draw(batch);

        //salida.sprite.draw(batch);


        if(checkEnemyCollision()) {
            for (Slime s : slimes) {
                stateTime += Gdx.graphics.getDeltaTime();
                if (s.state != true) {

                    TextureRegion currenFrame = (TextureRegion) s.attackingAnimation.getKeyFrame(stateTime, true);

                    batch.draw(currenFrame, s.getX(), s.getY());
                    //v.boundsVaca.setPosition(v.x, v.y);
                    //v.update();
                }
            }

        }else if (!checkEnemyCollision()) {

            for (Slime s : slimes) {

                stateTime += Gdx.graphics.getDeltaTime();
                if (s.state != true) {


                    TextureRegion currenFrame = (TextureRegion) s.walkingAnimation.getKeyFrame(stateTime, true);
                    batch.draw(currenFrame, s.getX(), s.getY());
                    //v.boundsVaca.setPosition(v.x, v.y);
                    //v.update();
                }
            }
        }


        batch.end();
        checkEnemyCollision();
        checkExitCollision();
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();
        labeld.setText(String.format("%01d",ivan.documents));
        label.setText(String.format("%01d",ivan.life));

        if (estado == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }

    }


    public void checkExitCollision(){
        if (jugBounds.overlaps(salida.salidaBounds)){
            pantallaInicio.setScreen (new PantallaCreditos(pantallaInicio));

        }
    }


    private boolean checkEnemyCollision() {
        for(Slime s : slimes){
            if (jugBounds.overlaps(s.boundsSlime)){
                if(ivan.movimiento == "ATAQUE"){

                    s.hitOnHead();
                    return true;
                } else {
                    //ivan.estado = "atacado";

                    ivan.damage(1);
                    if(ivan.getLife() <= 0){
                        pantallaInicio.setScreen (new PantallaGameOver(pantallaInicio));

                    }
                    return true;
                }

            }
        }
        return false;
    }



    @Override
    public void pause() {
        this.estado = EstadoJuego.PAUSADO;
    }

    @Override
    public void resume() {
        this.estado = EstadoJuego.JUGANDO;

    }

    @Override
    public void dispose() {
        escenaHUD.dispose();
        mapa.dispose();
        batch.dispose();


    }
    class EscenaPausa extends Stage {
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
