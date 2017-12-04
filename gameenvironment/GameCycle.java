package gameenvironment;

/**
 * Created by Pierre on 2016-11-27.
 */
public class GameCycle {

    private int fps;
    private double timePerTick;
    private double cycle;
    private long stopTime;
    private long startTime;
    private static final int NANOSEC_PER_SEC = 1000000000;


    public GameCycle(int fps) {
        this.fps = fps;
        timePerTick = NANOSEC_PER_SEC / fps;
        cycle = 0;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        stopTime = System.nanoTime();
        calculateCycle();
    }

    private void calculateCycle() {
        cycle = cycle + (stopTime - startTime) / timePerTick;
        startTime = stopTime;
    }

    public boolean cycleElapsed() {
        if(cycle >= 1){
            cycle = 0;
            return true;
        }else{
            return false;
        }
    }


}
