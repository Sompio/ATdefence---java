package userinput;

/**
 * Created by dv15oes (aka JOE) on 2016-11-30.
 *
 * Added class varaiable s. 2016-11-30 JOE
 * Added methods addString, getString. 2016-11-30 JOE
 *
 */
public class ATDEvent {
    private String id;
    private String level;
    private int x;
    private int y;


    /**
     * Constructor that takes a string.
     * @param id String to add to this event.
     */
    public ATDEvent(String id) {
        this.setId(id);
    }

    /**
     * Add a string
     * @param id String to add.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the string, or null if no string has been added.
     * @return String or null.
     */
    public String getId() {
        return this.id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return this.level;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) { this.y = y; }

    public int getY() {
        return this.y;
    }
}
