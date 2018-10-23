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
        //Credits
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.valueOf("#77ff72");
        label = new Label("Credits", labelStyle);
        stage.addActor(label);
        //
        Label.LabelStyle labelStyle1 = new Label.LabelStyle();
        labelStyle1.font = font;
        labelStyle1.fontColor = Color.valueOf("#72ffcb");
        Label label1 = new Label("*The Team",labelStyle1);
        label1 .setPosition(label.getWidth(), 6*ALTO/8);
        label1.setAlignment(Align.center);
        label.setPosition(ANCHO/2- label.getWidth()/2, ALTO-label.getPrefHeight());
        label.setAlignment(Align.left);
        stage.addActor(label1);
        //Coders
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = font;
        labelStyle2.fontColor = Color.valueOf("49fbff");
        Label label2 = new Label("==> Coders:", labelStyle2);
        label2.setPosition(label2.getWidth()/2, 5*ALTO/8);
        label2.setAlignment(Align.left);
        stage.addActor(label2);



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
