package itesm.cem.revisionistic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaGameOver extends Pantalla {

    private final PantallaInicio pantallaInicio;
    private Texture textFondo;
    private Stage escenaGameOver;
    private Texture baseGameOver;
    private Texture textGameOver;
    private Texture optionsGameOver;
    public Music music;

    public PantallaGameOver(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        crearEscena();
        cargarMusica();
        Gdx.input.setInputProcessor(escenaGameOver);

    }

    private void cargarMusica() {
        AssetManager manager = new AssetManager();
        manager.load("audio/Menu.mp3", Music.class);
        manager.finishLoading();
        music = manager.get("audio/Menu.mp3");
        music.setLooping(true);
        music.play();
    }

    private void crearEscena() {
        batch = new SpriteBatch();
        escenaGameOver = new Stage(vista);
        textFondo = new Texture("GameOver\\GameOverScreen_BG.png");
        baseGameOver = new Texture("GameOver\\GameOverScreen_Skull.png");
        textGameOver = new Texture("GameOver\\GameOverScreen_Title.png");
        optionsGameOver = new Texture("GameOver\\GameOverScreen_TryAgainScreen.png");

        //btn regreso
        Texture BtnBack = new Texture("GameOver\\GameOverScreen_TryAgainNO.png");
        TextureRegionDrawable tback = new TextureRegionDrawable(new TextureRegion(BtnBack));

        Texture BtnBackOP = new Texture("GameOver\\GameOverScreen_TryAgainNOPressed.png");
        TextureRegionDrawable tbackop = new TextureRegionDrawable(new TextureRegion(BtnBackOP));

        ImageButton btnBack = new ImageButton(tback,tbackop);
        btnBack.setPosition(ANCHO/2-250, ALTO/2-100);
        btnBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                music.dispose();
                pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            }
        });

        //btnJugarDeNuevo
        Texture BtnPlayAgain = new Texture("GameOver\\GameOverScreen_TryAgainYES.png");
        TextureRegionDrawable tplayAgain = new TextureRegionDrawable(new TextureRegion(BtnPlayAgain));

        Texture BtnPlayAgainOP = new Texture("GameOver\\GameOverScreen_TryAgainYESPressed.png");
        TextureRegionDrawable tplayAgainOp = new TextureRegionDrawable(new TextureRegion(BtnPlayAgainOP));

        ImageButton btnPlayAgin = new ImageButton(tplayAgain,tplayAgainOp);
        btnPlayAgin.setPosition(ANCHO/2, ALTO/2-100);
        btnPlayAgin.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                music.dispose();
                pantallaInicio.setScreen(new PantallaMapa(pantallaInicio));
            }
        });
        escenaGameOver.addActor(btnBack);
        escenaGameOver.addActor(btnPlayAgin);
    }

    @Override
    public void render(float delta) {

        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        batch.draw(textFondo,0,0);
        batch.draw(baseGameOver,ANCHO/2-baseGameOver.getWidth()/2, ALTO/2-baseGameOver.getHeight()/2);
        batch.draw(textGameOver,ANCHO/2-textGameOver.getWidth()/2, ALTO-textGameOver.getHeight());
        batch.draw(optionsGameOver, ANCHO/2-optionsGameOver.getWidth()/2,ALTO/2-optionsGameOver.getHeight()/2);

        batch.end();
        escenaGameOver.draw();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        escenaGameOver.dispose();
        textFondo.dispose();
        textGameOver.dispose();
        baseGameOver.dispose();
        batch.dispose();
        music.dispose();
    }

}
