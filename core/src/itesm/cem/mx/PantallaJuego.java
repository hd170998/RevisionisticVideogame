package itesm.cem.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import itesm.cem.mx.PantallaInicio;

import java.security.AlgorithmParameterGenerator;

class PantallaJuego extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Sprite BtnCargar;
    private Sprite BtnNuevo;
    private Texture Title;
    //fondo de pantalla
    private Texture textFondo;
    private Stage escenaMenu;
    //boton regreso


    public PantallaJuego(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        crearescena();
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearescena() {
        batch = new SpriteBatch();
        escenaMenu=new Stage(vista);
        textFondo = new Texture("SecondScreen.png");
        BtnCargar=new Sprite(new Texture("CargarJuegoBtn01.png"));
        BtnCargar.setPosition(ANCHO/16,(ALTO/2));
        BtnNuevo=new Sprite(new Texture("NuevoJuegoBtn01.png"));
        BtnNuevo.setPosition(ANCHO/16,(ALTO/2)-100);
        //fondo
        Title = new Texture("littleTitle.png");
        //btn regreso
        Texture BtnBack=new Texture("BackButton01.png");
        TextureRegionDrawable tback= new TextureRegionDrawable(new TextureRegion(BtnBack));
        Texture BtnBackOP = new Texture("BackButton02HOVER.png");
        TextureRegionDrawable tbackop = new TextureRegionDrawable(new TextureRegion(BtnBackOP));
        ImageButton btnBack = new ImageButton(tback,tbackop);
        btnBack.setPosition(0,ALTO-BtnBack.getHeight());
        btnBack.addListener(new ClickListener(){
                    public void clicked(InputEvent event, float x, float y) {
                         super.clicked(event, x, y);
                         pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            }
        });
        escenaMenu.addActor(btnBack);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        batch.end();
        escenaMenu.draw();

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
