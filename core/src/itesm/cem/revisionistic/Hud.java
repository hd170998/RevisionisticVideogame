package itesm.cem.revisionistic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import itesm.cem.revisionistic.Personaje;


public class Hud implements Disposable {
    // Texto contadores
    private float timeCount;
    private static Integer score;
    private Integer worldTimer;
    private Label countDownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label naughtyLabel;

    //
    public Stage stage;
    private Viewport viewport;

    private Button btnRight;
    private Button btnA;
    private Button btnB;
    private Button btnPausa;
    private Button btnLeft;
    private Button btnUp;
    private Button btnDown;

    @Override
    public void dispose() {

    }

    // Botones
   /* private Controller controller;
    private Image pause;

    public Hud(SpriteBatch sb){
        worldTimer = 000;
        timeCount = 0;
        score = 0;
        viewport = new StretchViewport(ANCHO_JUEGO, ALTO_JUEGO,new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        naughtyLabel = new Label("Jhony", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        /*
        controller = new Controller(0);
        controller.setColor(1,1,1,0.7f);
         */


        /*createButtons();

        table.add(naughtyLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(countDownLabel).expandX();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer++;
            countDownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void createButtons(){
        // Boton A
        btnA = new Button("Botones/btnA.png", "Botones/APres.png", 0.75f*ANCHO_JUEGO, 10);

        // Botón pausa
        btnPausa = new Button("Botones/Pause.png", "Botones/PausaPres.png", ANCHO_JUEGO*0.80f, ALTO_JUEGO*0.83f);
        // Boton B
        btnB = new Button("Botones/btnB.png", "Botones/BPres.png", 0.89f*ANCHO_JUEGO, 30);

        // Boton right
        btnRight = new Button("Botones/btnRight.png", "Botones/btnRightPress.png", 150, 65);

        // Boton left
        btnLeft = new Button("Botones/btnLeft.png", "Botones/btnLeftPress.png", 20, 65);

        // Boton up
        btnUp = new Button("Botones/btnUp.png", "Botones/btnUpPress.png", 85, 130);

        // Boton down
        btnDown = new Button("Botones/btnDown.png", "Botones/btnDownPress.png", 85, 5);

        stage.addActor(btnA.getImageButton());
        stage.addActor(btnPausa.getImageButton());
        stage.addActor(btnB.getImageButton());
        stage.addActor(btnRight.getImageButton());
        stage.addActor(btnLeft.getImageButton());
        stage.addActor(btnUp.getImageButton());
        stage.addActor(btnDown.getImageButton());
    }

    public ImageButton getBtnA() {
        return btnA.getImageButton();
    }

    public ImageButton getBtnB() {
        return btnB.getImageButton();
    }

    public ImageButton getBtnPausa() {
        return btnPausa.getImageButton();
    }

    public ImageButton getBtnRight() {
        return btnRight.getImageButton();
    }

    public ImageButton getBtnLeft() {
        return btnLeft.getImageButton();
    }

    public ImageButton getBtnUp() {
        return btnUp.getImageButton();
    }

    public ImageButton getBtnDown() {
        return btnDown.getImageButton();
    }*/

}
