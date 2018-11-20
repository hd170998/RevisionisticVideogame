package itesm.cem.revisionistic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaCreditos extends Pantalla {
    private PantallaInicio pantallaInicio;
    private BitmapFont font;
    private Texture textFondo;
    private float elapsedTime;
    private Texture creditos;
    private Stage escenaCreditos;
    public PantallaCreditos(PantallaInicio pantallaInicio) { this.pantallaInicio = pantallaInicio;}
    @Override
    public void show() {
        textFondo = new Texture("Creditos/Credits_BG.png");
        //fondo
        creditos = new Texture("Creditos/ProvisionalCreditsImage.png");
        elapsedTime = 0;
        escenaCreditos = new Stage(vista);
        Texture BtnBack=new Texture("BackButton01.png");
        TextureRegionDrawable tback= new TextureRegionDrawable(new TextureRegion(BtnBack));
        Texture BtnBackOP = new Texture("BackButton02HOVER.png");
        TextureRegionDrawable tbackop = new TextureRegionDrawable(new TextureRegion(BtnBackOP));
        ImageButton btnBack = new ImageButton(tback,tbackop);
        btnBack.setPosition(btnBack.getWidth()/3,ALTO-(BtnBack.getHeight()+10));
        btnBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                pantallaInicio.setScreen(new PlayScreen(pantallaInicio));
            }
        });
        escenaCreditos.addActor(btnBack);


    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(textFondo,0,0);
        batch.draw(creditos, 0, -creditos.getHeight()/2+(60*elapsedTime));
        batch.end();
        escenaCreditos.draw();
        Gdx.input.setInputProcessor(escenaCreditos);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        creditos.dispose();
        batch.dispose();
        textFondo.dispose();


    }
}
