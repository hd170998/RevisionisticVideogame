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

public class PantallaNivel extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Texture backGround;
    private Stage escenaNivel;
    private Texture title;
    private SpriteBatch batch;

    public PantallaNivel(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        crearEscena();
        Gdx.input.setInputProcessor(escenaNivel);


    }

    private void crearEscena() {
        batch = new SpriteBatch();
        escenaNivel = new Stage(vista);
        backGround = new Texture("Level/SelectLevelBG.png");
        title = new Texture("Level/SelectLevelTitle.png");
        Drawable regionReac = new TextureRegionDrawable(new TextureRegion(new Texture("Level/Level_Forest.png")));
        ImageButton levelReac = new ImageButton(regionReac);
        levelReac.setPosition(20,ALTO/2-levelReac.getHeight()/2);
        levelReac.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pantallaInicio.setScreen(new PlayScreen(pantallaInicio));
                super.clicked(event, x, y);
            }
        });
        Drawable regionPlay2 = new TextureRegionDrawable(new TextureRegion(new Texture("Level/Level_Village.png")));
        ImageButton levelPlay2 = new ImageButton(regionPlay2);
        levelPlay2.setPosition(ANCHO/2-levelPlay2.getWidth()/2,ALTO/2-levelReac.getHeight()/2);
        levelPlay2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pantallaInicio.setScreen(new PlayScreen2(pantallaInicio));
                super.clicked(event, x, y);
            }
        });
        Drawable regionPlay3 = new TextureRegionDrawable(new TextureRegion(new Texture("Level/Level_Lab.png")));
        ImageButton levelPlay3 = new ImageButton(regionPlay3);
        levelPlay3.setPosition(ANCHO-20-levelPlay3.getWidth(),ALTO/2-levelReac.getHeight()/2);
        levelPlay3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pantallaInicio.setScreen(new PlayScreen3(pantallaInicio));
                super.clicked(event, x, y);
            }
        });


        escenaNivel.addActor(levelPlay2);
        escenaNivel.addActor(levelReac);
        escenaNivel.addActor(levelPlay3);


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
