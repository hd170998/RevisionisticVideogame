package itesm.cem.mx;

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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class PantallaOpciones extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Texture textFondo;
    private Stage escenaOpciones;
    private Texture baseOpciones;
    public Music music;

    public PantallaOpciones(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        crearEscena();
        cargarMusica();
        Gdx.input.setInputProcessor(escenaOpciones);

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
        escenaOpciones = new Stage(vista);
        textFondo = new Texture("SecondScreen.png");
        baseOpciones = new Texture("Options/SettingsBase.png");

        //btn regreso
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
        Drawable regionMusic = new TextureRegionDrawable(new TextureRegion(new Texture("Options\\ButtonMusic_On.png")));
        Drawable regionMusicOP = new TextureRegionDrawable(new TextureRegion( new Texture("Options\\ButtonMusic_Off.png")));
        ImageButton btnMusic = new ImageButton(regionMusic,regionMusicOP);
        btnMusic.setPosition(ANCHO/2-btnMusic.getWidth()/2,5*ALTO/8);
        Drawable regionSound = new TextureRegionDrawable(new TextureRegion(new Texture("Options\\ButtonSoundFX_On.png")));
        Drawable regionSoundOP = new TextureRegionDrawable(new TextureRegion(new Texture("Options\\ButtonSoundFX_Off.png")));
        ImageButton btnSound = new ImageButton(regionSound,regionSoundOP);
        btnSound.setPosition(ANCHO/2-btnSound.getWidth()/2,4*ALTO/8);
        escenaOpciones.addActor(btnSound);

        Drawable regionCredits = new TextureRegionDrawable(new TextureRegion(new Texture("Options\\ButtonCredits_Normal.png")));
        Drawable regionCreditsOP = new TextureRegionDrawable(new TextureRegion(new Texture("Options\\ButtonCredits_Click.png")));
        ImageButton  btnCredits = new ImageButton(regionCredits, regionCreditsOP);
        btnCredits.setPosition(ANCHO/2-btnSound.getWidth()/2,1*ALTO/8);
        btnCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.dispose();
                pantallaInicio.setScreen (new PantallaCreditos(pantallaInicio));
            }
        });
        escenaOpciones.addActor(btnCredits);

        escenaOpciones.addActor(btnBack);
        escenaOpciones.addActor(btnMusic);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        batch.draw(baseOpciones,ANCHO/2-baseOpciones.getWidth()/2, ALTO/2-baseOpciones.getHeight()/2);
        batch.end();
        escenaOpciones.draw();

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
        escenaOpciones.dispose();
        textFondo.dispose();
        baseOpciones.dispose();

    }
}
