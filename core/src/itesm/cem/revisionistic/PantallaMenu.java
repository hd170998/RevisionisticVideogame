package itesm.cem.revisionistic;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

class PantallaMenu extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Stage escenaMenu;
    private Texture textFondo;
    private Texture title;
    private SpriteBatch batch;
    public Music music;
    private float elapsedTime;
    private Texture logoTec;
    private Image tec;

    public PantallaMenu(PantallaInicio pantallaInicio) {
        this.pantallaInicio=pantallaInicio;
    }

    @Override
    public void show() {
        logoTec = new Texture("TecSplashScreen.png");
        tec = new Image(logoTec);
        elapsedTime=0;
        crearEscena();
        cargarMusica();
        Gdx.input.setInputProcessor(escenaMenu);
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
        escenaMenu=new Stage(vista);
        textFondo = new Texture("Menu/MainScreen.png");
        title = new Texture("Menu/LogoMainScreen.png");
        Image titulo = new Image(title);

// Botones
        Texture textBtnPlay=new Texture("Menu/BtnJugarBasico01.png");
        Texture BtnOpciones = new Texture("Menu/OpcionesBtn01.png");
        Texture BtnExtras = new Texture("Menu/ExtrasBtn01.png");
        //declarar regiones
        TextureRegionDrawable top= new TextureRegionDrawable(new TextureRegion(BtnOpciones));
        TextureRegionDrawable trd = new TextureRegionDrawable(new TextureRegion(textBtnPlay));
        TextureRegionDrawable tex = new TextureRegionDrawable(new TextureRegion(BtnExtras));
        //boton oprimido
        Texture textBtnPlayOprimido = new Texture("Menu/BtnJugarBasico02HOVER.png");
        Texture textBtnOpcionesOp = new Texture("Menu/OpcionesBtn02HOVER.png");
        Texture textBtnExtrasOp = new Texture("Menu/ExtrasBtn02HOVER.png");
        ///region Boton Oprimido
        TextureRegionDrawable trdOp = new TextureRegionDrawable(new TextureRegion(textBtnPlayOprimido));
        TextureRegionDrawable tropr = new TextureRegionDrawable(new TextureRegion(textBtnOpcionesOp));
        TextureRegionDrawable  trexop = new TextureRegionDrawable(new TextureRegion(textBtnExtrasOp));
        //creo boton
        ImageButton btnOp = new ImageButton(top,tropr);
        ImageButton btnPlay = new ImageButton(trd, trdOp);
        ImageButton btnEX = new ImageButton(tex,trexop);
        //Ubicar Botones
        btnPlay.setPosition(ANCHO/16,(ALTO/2)-(btnPlay.getHeight()/2));
        btnOp.setPosition(ANCHO-btnOp.getWidth(), ALTO/2+ btnOp.getHeight()/2);
        btnEX.setPosition(ANCHO-btnEX.getWidth(), ALTO/2-btnEX.getHeight());

        //accion del boton
        btnPlay.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);

                                    pantallaInicio.setScreen(new PantallaJuego(pantallaInicio));
                                }
                            }
        );
        btnEX.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                pantallaInicio.setScreen(new PantallaExtras(pantallaInicio));
            }
                          }
        );
        btnOp.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                pantallaInicio.setScreen(new PantallaOpciones(pantallaInicio));
            }

        });
        titulo.setPosition((ANCHO / 2) - title.getWidth() / 2, ALTO - title.getHeight());
        ///metodo de dibujo para los botones
        escenaMenu.addActor(btnPlay);
        escenaMenu.addActor(btnOp);
        escenaMenu.addActor(btnEX);
        escenaMenu.addActor(titulo);
        // Animacion dientes

    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined); //escalar la pantalla y la vista correctamente al dicpositivo
        elapsedTime+= Gdx.graphics.getDeltaTime();
        batch.begin();
        if (elapsedTime<3) {
            batch.draw(logoTec, 0, 0);
        }
        if (elapsedTime>3) {
            batch.draw(textFondo, 0, 0);
            batch.draw(title,(ANCHO / 2) - title.getWidth() / 2, ALTO - title.getHeight());
            escenaMenu.draw();
        }
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
        textFondo.dispose();
        title.dispose();
        escenaMenu.dispose();
        music.dispose();
        batch.dispose();

    }
}