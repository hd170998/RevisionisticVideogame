package itesm.cem.revisionistic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
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

public class PlayScreen  extends Pantalla{
    private static final float ANCHO_MAPA = 4800;
    private static final float ALTO_MAPA = 2560;
    private TiledMap mapa;
    private final PantallaInicio pantallaInicio;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMapRenderer tiledMapRenderer;
    private Personaje ivan;
    private MapLayer objectLayer;
    private Label label;
    private BitmapFont font;
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    // HUD con una escena para los botones y componentes
    private Stage escenaHUD;


    public PlayScreen(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }
    @Override
    public void show() {
        ivan = new Personaje(new Texture("1V4N_Xaxis.png"));
        ivan.setLife(100);
        ivan.setDocuments(0);
        cargarMapa();
        crearHUD();
        Gdx.input.setInputProcessor(escenaHUD);


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
        final TextureMapObject character = (TextureMapObject)mapa.getLayers().get("1V4N").getObjects().get(0);
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
        Drawable regionAtaque = new TextureRegionDrawable(new TextureRegion(new Texture("atacar.png")));
        Drawable regionAtaqueOP = new TextureRegionDrawable(new TextureRegion( new Texture("atacar.png")));
        final ImageButton btnAtaque = new ImageButton(regionAtaque,regionAtaqueOP);
        btnAtaque.setPosition(ANCHO-btnAtaque.getWidth(),btnAtaque.getHeight());
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
        manager.load("ForestStuff/ForestMap.tmx",TiledMap.class);
        manager.finishLoading(); // Espera
        mapa = manager.get("ForestStuff/ForestMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(mapa);
        objectLayer = mapa.getLayers().get("1V4N");
        TextureMapObject IvanO = new TextureMapObject(ivan.getAnimation());
        objectLayer.getObjects().add(IvanO);

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
        batch.setProjectionMatrix(camaraHUD.combined);
        escenaHUD.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
