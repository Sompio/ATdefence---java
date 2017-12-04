package gameenvironment;

import gamecomponents.GameAttributes;
import gamecomponents.GameObjects;
import gui.ErrorMessage;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by Pierre on 2016-11-27.
 */
public class Game implements Runnable {

    private Canvas gameScreen;
    private GameObjects gameObjects;
    private GameAttributes gameAttributes;
    private ErrorMessage errorMessage;

    private boolean gameRunning;
    private Thread gameLoop;
    private GameCycle cycle;

    private BufferStrategy buffer;
    private Graphics graphic;
    private boolean atStartOfLevel = true;

    public Game(Canvas gameScreen, GameObjects go, GameAttributes ga, int fps) {
        this.gameScreen = gameScreen;
        this.gameObjects = go;
        this.gameAttributes = ga;
        errorMessage = new ErrorMessage();
        gameRunning = false;
        cycle = new GameCycle(fps);
    }

    public synchronized void start(){
        if(!gameRunning){
            gameLoop = new Thread(this);
            gameRunning = true;
            gameLoop.start();
        }
    }

    public synchronized void stop(){
        if(gameRunning){
            gameRunning = false;
            try {
                gameLoop.join();
            } catch (InterruptedException e) {
                errorMessage.display("Thread Error", "Error joining thread");
            }
        }
    }

    @Override
    public void run() {

        cycle.startTimer();

        while(gameRunning){

            cycle.stopTimer();
            if(atStartOfLevel){
                gameAttributes.getPlayer().setCredits(gameAttributes.getLevel().getRules().getCreditsAtStart());
                atStartOfLevel = false;
            }

            if(cycle.cycleElapsed()){
                if(!gameAttributes.getPause()){
                    update();
                }
                render();
            }

        }

        stop();

    }

    /* Initializes the screen at the beginning of the game. */
    public void initializeGame(){

    }

    /* Draws stuff on the screen */
    public void render(){
        try {
            buffer = gameScreen.getBufferStrategy();
            if (buffer == null) {
                gameScreen.createBufferStrategy(3);
                return;
            }
            graphic = buffer.getDrawGraphics();
            /*Clears the screen before drawing new stuff */
            graphic.clearRect(0, 0, 1200, 900);

            /*Draw stuff here*/
            gameAttributes.getLevel().draw(graphic);
            gameObjects.drawGoal(graphic);
            gameObjects.drawCrossRoad(graphic);
            gameObjects.drawTowers(graphic);
            gameObjects.drawTroup(graphic);

            /*End drawing here*/
            buffer.show();
            graphic.dispose();
        } catch (IllegalStateException e) {
            return;
        }
    }

    /* Moves stuff every time unit */
    public void update(){
        gameObjects.moveTroup();
        gameAttributes.getPlayer().addCredits(gameObjects.getGoal().getAndResetCredits());
        //kolla om spelet är slut
    }


}
