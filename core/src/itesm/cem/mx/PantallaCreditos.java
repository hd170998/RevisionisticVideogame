package itesm.cem.mx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PantallaCreditos extends Pantalla {
    private Label label;
    private PantallaInicio pantallaInicio;
    private BitmapFont font;
    private Texture textFondo;
    private Texture Title;
    private Stage stage;
    public PantallaCreditos(PantallaInicio pantallaInicio) { this.pantallaInicio = pantallaInicio;}
    @Override
    public void show() {

        textFondo = new Texture("Creditos/Credits_BG.png");
        //fondo
        Title = new Texture("LogoSecondScreen.png");
        stage = new Stage(vista);
        font = new BitmapFont(Gdx.files.internal("Letra.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.valueOf("#77ff72");
        label = new Label("Credits", labelStyle);
        label.setPosition(ANCHO/2- label.getWidth()/2, ALTO-label.getPrefHeight());
        label.setAlignment(Align.center);
        stage.addActor(label);


    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        batch.draw(Title,ANCHO-Title.getWidth(),ALTO-Title.getHeight());
        stage.draw();
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
        stage.dispose();

    }
}
