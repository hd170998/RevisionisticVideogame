package mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaExtras extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Texture textFondo;
    private Texture title;
    private Texture box;
    private Stage escenaExtras;
    private Texture lock;
    private Table popUp;
    public Music music;


    public PantallaExtras(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        escenaExtras();
        cargarMusica();
        Gdx.input.setInputProcessor(escenaExtras);

    }

    private void cargarMusica() {
        AssetManager manager = new AssetManager();
        manager.load("audio/Menu.mp3", Music.class);
        manager.finishLoading();
        music = manager.get("audio/Menu.mp3");
        music.setLooping(true);
        music.play();
    }


    private void escenaExtras() {
        batch = new SpriteBatch();
        escenaExtras=new Stage(vista);
        textFondo = new Texture("Extras/Screen_BG.png");
        title = new Texture("Extras/Screen_TitleGame.png");
        box = new Texture("Extras/Screen_DescriptionBox.png");
        lock = new Texture("Extras/Screen_LockedIllustration.png");
        Texture BtnBack=new Texture("BackButton01.png");
        TextureRegionDrawable tback= new TextureRegionDrawable(new TextureRegion(BtnBack));
        Texture BtnBackOP = new Texture("BackButton02HOVER.png");
        TextureRegionDrawable tbackop = new TextureRegionDrawable(new TextureRegion(BtnBackOP));
        ImageButton btnBack = new ImageButton(tback,tbackop);
        btnBack.setPosition(btnBack.getWidth()/3,ALTO-(BtnBack.getHeight()+10));
        btnBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                music.dispose();
                pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            }
        });
        escenaExtras.addActor(btnBack);

    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined); //escalar la pantalla y la vista correctamente al dicpositivo
        batch.begin();
        batch.draw(textFondo,0,0);
        batch.draw(title,ANCHO-title.getWidth(), ALTO-title.getHeight());
        batch.draw(box,ANCHO/8,box.getHeight()/8);
        batch.draw(lock, ANCHO/2, box.getHeight()/8);
        batch.end();
        escenaExtras.draw();
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
    public void dispose() {
    }
}
