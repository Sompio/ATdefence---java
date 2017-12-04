import gamecomponents.GameAttributes;
import gamecomponents.GameObjects;
import gameenvironment.Game;
import userinput.*;
import gui.GUI;

/**
 * Created by Pierre on 2016-11-29.
 */
public class ATDefense {
    public static void main(String[] args) throws Exception {
        GameAttributes ga = new GameAttributes();
        GameObjects go = new GameObjects();

        ProcessUserInput pui = new ProcessUserInput(ga, go);
        OnATDEvent oae = new OnATDEvent(pui);

        String windowTitle = "AntiTower Defense";

        GUI g = new GUI(windowTitle, oae, ga);
        pui.setCreditsLabel(g.getCreditsLabel());
        Game game = new Game(g.getCanvas(),go, ga, 60);
        game.start();
    }
}
