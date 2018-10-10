package itesm.cem.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaMapa extends Pantalla {
    private static final float ANCHO_MAPA = 4800;
    private final Juego juego;

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
        borrarPantalla(0.35f,0.55f,1);
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
}
