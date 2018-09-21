package itesm.cem.mx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import itesm.cem.mx.PantallaInicio;

import java.security.AlgorithmParameterGenerator;

class PantallaJuego extends Pantalla {
    private final PantallaInicio pantallaInicio;
    private Sprite BtnCargar;
    private Sprite BtnNuevo;
    private Texture Title;
    //fondo de pantalla
    private Texture textFondo;
    //boton regreso
    private Texture textBtnRegreso;

    private Animation player;
    private float stateTime;
    private SpriteBatch spriteBatch;

    public PantallaJuego(PantallaInicio pantallaInicio) {
        this.pantallaInicio = pantallaInicio;
    }

    @Override
    public void show() {
        BtnCargar=new Sprite(new Texture("CargarJuegoBtn01.png"));
        BtnCargar.setPosition(ANCHO/16,(ALTO/2));
        BtnNuevo=new Sprite(new Texture("NuevoJuegoBtn01.png"));
        BtnNuevo.setPosition(ANCHO/16,(ALTO/2)-100);
        //fondo
        textFondo = new Texture("SecondScreen.png");
        Title = new Texture("littleTitle.png");
        //btn regreso
        textBtnRegreso = new Texture("BackButton01.png");
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(textFondo,0,0);
        batch.draw(Title,ANCHO-Title.getWidth(),ALTO-Title.getHeight());
        batch.draw(textBtnRegreso, ANCHO/116, ALTO-textBtnRegreso.getHeight());
        BtnCargar.draw(batch);
        BtnNuevo.draw(batch);
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

    }

    class ProcesadorEntrada implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            pantallaInicio.setScreen(new PantallaMenu(pantallaInicio));
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
