package itesm.cem.revisionistic;

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

class PantallaJuego extends Pantalla {
    private final PantallaInicio pantallaInicio;
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
        Gdx.input.setCatchBackKey(true);
        crearescena();
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void crearescena() {
        batch = new SpriteBatch();
        escenaMenu=new Stage(vista);
        //aniamcion Transicion
        textFondo = new Texture("SecondScreen.png");
        //fondo
        Title = new Texture("LogoSecondScreen.png");
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
        //boton cargar juego
        Drawable regionCargar = new TextureRegionDrawable(new TextureRegion(new Texture("CargarJuegoBtn01.png")));
        Drawable regionCargarOp = new TextureRegionDrawable(new TextureRegion(new Texture("CargarJuegoBtn02HOVER.png")));
        ImageButton btnCargar = new ImageButton(regionCargar,regionCargarOp);
        btnCargar.setPosition(ANCHO/18,ALTO/2);
        //boton juego nuevo
        Drawable regionNuevo = new TextureRegionDrawable(new TextureRegion(new Texture("NuevoJuegoBtn01.png")));
        Drawable regionNuevoOP = new TextureRegionDrawable(new TextureRegion( new Texture("NuevoJuegoBtn02HOVER.png")));
        ImageButton btnNuevo = new ImageButton(regionNuevo,regionNuevoOP);
        btnNuevo.setPosition(ANCHO/18, ALTO/2 -100);
        btnNuevo.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PlayScreen(pantallaInicio));
            }
        });
        btnCargar.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pantallaInicio.setScreen(new PantallaNivel(pantallaInicio));
            }
        });
        escenaMenu.addActor(btnNuevo);
        escenaMenu.addActor(btnBack);
        escenaMenu.addActor(btnCargar);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        batch.draw(Title,ANCHO-Title.getWidth(),ALTO-Title.getHeight());
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
        escenaMenu.dispose();
        textFondo.dispose();
        Title.dispose();
        batch.dispose();

    }
}
