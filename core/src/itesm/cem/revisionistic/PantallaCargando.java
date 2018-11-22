package itesm.cem.revisionistic;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class PantallaCargando extends Pantalla{
    private final PantallaInicio pantallaInicio;
    private Texture cargando;
    private static final AssetManager manager = new AssetManager();

    public PantallaCargando(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        cargando = new Texture("Loading.png");

    }

    @Override
    public void render(float delta) {
        manager.load("audio/Forest.mp3", Music.class);
        manager.load("audio/saw-audio.mp3",Sound.class);
        manager.load("audio/saw-audio.mp3",Sound.class);
        manager.load("audio/Documento.mp3", Sound.class);
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("ForestStuff/ForestMap.tmx",TiledMap.class);
        //manager.load("MapLaboratory/LabMap01.tmx", TiledMap.class);
        manager.load("VillageStuff/VillageMap.tmx", TiledMap.class);
        manager.finishLoading(); // Espera
        if(manager.update()) {
            pantallaInicio.setScreen(new PlayScreen(pantallaInicio));

        }
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(cargando,0,0);
        batch.end();

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
