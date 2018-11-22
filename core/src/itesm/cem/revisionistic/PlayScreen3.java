package itesm.cem.revisionistic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen3 extends Pantalla{
    private final PantallaInicio pantallaInicio;
    private static final float ANCHO_MAPA = 2560;
    private static final float ALTO_MAPA = 1280;
    private TiledMap mapa;
    private TiledMapRenderer tiledMapRenderer;
    private Personaje ivan;
    private MapLayer objectLayer;
    private Label label, labeld;
    private BitmapFont font;
    private OrthographicCamera camaraHUD;
    private Viewport vistaHUD;
    // HUD con una escena para los botones y componentes
    private Stage escenaHUD;
    private Sound saw;
    private EstadoJuego estado;
    private Music music;
    private PlayScreen2.EscenaPausa escenaPausa;
    private MapObjects objetos;
    private Array<Vaca> vacas = new Array<Vaca>();
    private Rectangle jugBounds;

    public PlayScreen3(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }


    @Override
    public void show() {
        /*Gdx.input.setCatchBackKey(true);
        ivan = new Personaje(new Texture("1V4N_Xaxis.png"));
        ivan.setX(1000);
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
        cargarMapa();*/

    }

    @Override
    public void render(float delta) {

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
