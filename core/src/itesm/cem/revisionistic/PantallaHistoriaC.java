package itesm.cem.revisionistic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class PantallaHistoriaC extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Texture historia;
    private float elapsedtime;
    private float posY=-6030;
    public PantallaHistoriaC(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        historia = new Texture("FullIntroPanels.png");
        elapsedtime=0;



    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        elapsedtime+= Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(historia,0,-6030+posY);
        posY=+(elapsedtime*280);
        batch.end();
        if (posY>5760){
            pantallaInicio.setScreen(new PantallaCargando(pantallaInicio));
        }




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
