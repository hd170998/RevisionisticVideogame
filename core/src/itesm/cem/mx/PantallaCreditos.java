package itesm.cem.mx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaCreditos extends Pantalla {
    private PantallaInicio pantallaInicio;
    private BitmapFont font;
    private Texture textFondo;
    private float elapsedTime;
    private Texture creditos;
    public PantallaCreditos(PantallaInicio pantallaInicio) { this.pantallaInicio = pantallaInicio;}
    @Override
    public void show() {
        textFondo = new Texture("Creditos/Credits_BG.png");
        //fondo
        creditos = new Texture("Creditos/ProvisionalCreditsImage.png");
        elapsedTime = 0;

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
