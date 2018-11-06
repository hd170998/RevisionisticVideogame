package mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaNivel extends Pantalla {
    private Texture backGround;
    private Stage escenaNivel;
    private Texture title;
    private SpriteBatch batch;

    public PantallaNivel(PantallaInicio pantallaInicio) {
    }

    @Override
    public void show() {
        crearEscena();
        Gdx.input.setInputProcessor(escenaNivel);


    }

    private void crearEscena() {
        batch = new SpriteBatch();
        escenaNivel = new Stage(vista);
        backGround = new Texture("SelectLevelBG.png");
        title = new Texture("SelectLevelTitle.png");
        Drawable regionReac = new TextureRegionDrawable(new TextureRegion(new Texture("Level_Reactor")));
        Drawable regionReavOP = new TextureRegionDrawable(new TextureRegion( new Texture("Level_Reactor.png")));
        ImageButton levelReac = new ImageButton(regionReac,regionReavOP);
        levelReac.setPosition(ANCHO-10,10);


    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(backGround,0,0);
        batch.draw(title,(ANCHO/2)-title.getWidth()/2, ALTO-title.getHeight());
        batch.end();
        escenaNivel.draw();

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
