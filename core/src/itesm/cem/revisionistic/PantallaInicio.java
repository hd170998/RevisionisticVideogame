package itesm.cem.revisionistic;
import com.badlogic.gdx.Game;

public class PantallaInicio extends Game {


    @Override
    public void create() {
        setScreen(new PantallaMenu(this));
    }
}
