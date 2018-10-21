package itesm.cem.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import javafx.scene.control.Alert;

public class PantallaOpciones extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Texture textFondo;
    private Stage escenaOpciones;
    private Texture baseOpciones;

    public PantallaOpciones(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        crearEscena();
        Gdx.input.setInputProcessor(escenaOpciones);

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
                pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            }
        });
        Drawable regionMusic = new TextureRegionDrawable(new TextureRegion(new Texture("ButtonMusic_On.png")));
        Drawable regionMusicOP = new TextureRegionDrawable(new TextureRegion( new Texture("ButtonMusic_Off.png")));
        ImageButton btnMusic = new ImageButton(regionMusic,regionMusicOP);
        btnMusic.setPosition(ANCHO/2-btnMusic.getWidth()/2,5*ALTO/8);

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

    }
}
