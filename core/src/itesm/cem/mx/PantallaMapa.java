package itesm.cem.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaMapa extends Pantalla {
    private static final float ANCHO_MAPA = 4800;
    private final Juego juego;
    private EstadoJuego estado;
    private EscenaPausa escenaPausa;

    // Mapas
    private TiledMap mapa;      // El mapa
    private OrthogonalTiledMapRenderer renderer;    // Dibuja el mapa
    private Personaje mario;    // Mario, lo controla el usuario

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

        // El input lo maneja la escena

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
        borrarPantalla(0f,0,0);
        batch.setProjectionMatrix(camara.combined);
        renderer.setView(camara);
        renderer.render();

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

    }

    private class EscenaPausa extends Stage{

        public EscenaPausa(Viewport viewport, Batch batch) {
            Pixmap pixmap = new Pixmap((int)(ANCHO*0.7f), (int)(ALTO*0.8f), Pixmap.Format.RGBA8888 );
            pixmap.setColor( 1f, 1f, 1f, 0.65f );
            pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
            Texture texturaRectangulo = new Texture( pixmap );
            pixmap.dispose();
            Image imgRectangulo = new Image(texturaRectangulo);
            imgRectangulo.setPosition(0.15f*ANCHO, 0.1f*ALTO);
            this.addActor(imgRectangulo);

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

            // Continuar
            Texture texturaBtnContinuar = new Texture("comun/btnContinuar.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(
                    new TextureRegion(texturaBtnContinuar));
            ImageButton btnContinuar = new ImageButton(trdContinuar);
            btnContinuar.setPosition(ANCHO/2-btnContinuar.getWidth()/2, ALTO/4);
            btnContinuar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Regresa al juego
                    estado = EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(new ProcesadorEntrada()); // No debería crear uno nuevo
                }
            });
            this.addActor(btnContinuar);
        }
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
}
