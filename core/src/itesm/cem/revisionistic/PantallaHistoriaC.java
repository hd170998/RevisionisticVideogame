package itesm.cem.revisionistic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class PantallaHistoriaC extends Pantalla {
    private Texture historia;
    private float elapsedtime;
    private float posY=-6030;
    public PantallaHistoriaC(PantallaInicio pantallaInicio) {
    }

    @Override
    public void show() {
        historia = new Texture("FullIntroPanels.png");
        elapsedtime=0;


    }

    @Override
    public void render(float delta) {
        elapsedtime+= Gdx.graphics.getDeltaTime();
        if ((Math.round(elapsedtime))%5==0){

            Gdx.app.log("Tiempo", ""+Math.round(elapsedtime));
            posY+=720;
        }
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(historia,0,posY);
        batch.end();


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
