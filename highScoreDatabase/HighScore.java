package highScoreDatabase;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by per-joelsompio on 10/12/16.
 */
public class HighScore {
    private String levelName;
    private String playerName;
    private int time;
    private int score;
    private int credits;

    private Connection conn = null;
    private Statement st = null;
    private PreparedStatement pst = null;

    private PlayerHighscore phs;

    /**
     *Constructor for Highscore. Parameters below
     * @param level a string containing the name of the level
     * @param playerName a string containing the name of the player
     * @param time an int containing the time for completing a level
     * @param score an int containing the calculated score for the player
     * @param credits an int containing the credits the player has when completing a level
     */
    public HighScore(String level, String playerName, int time, int score, int credits) {
        this.levelName = level;
        this.playerName = playerName;
        this.time = time;
        this.score = score;
        this.credits = credits;
    }

    /**
     * Connecting to sqlite3 database. The JDBC drivers provided in the zip-file
     * HIGHSCORE.db will be an empty database with a table called Highscore. it
     * is ready for use.
     */
    public void connectToDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:HIGHSCORE.db");
            System.out.println("Connected to db");
        } catch(ClassNotFoundException e) {
            System.out.println("Failed to load the drivers for the database");
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        } catch(SQLException e) {
            System.out.println("Failed to Connect to the database");
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * A method to add a player into the Highscore database.
     *
     * @return True or False
     */
    public boolean addToDB() {
        final String sqlAddHighScore = "INSERT INTO Highscore2 (Levelname, Playername, Time, Score, Credits)" +
                 "VALUES (?, ?, ?, ?, ?);";
        try {
            pst = conn.prepareStatement(sqlAddHighScore);
            pst.setString(1, levelName);
            pst.setString(2, playerName);
            pst.setInt(3, time);
            pst.setInt(4, score);
            pst.setInt(5, credits);
            pst.executeUpdate();
            //conn.close();
            System.out.println("Player added to DB");
            return true;
        } catch(SQLException e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.out.println("Failed to add player to Highscore");
            return false;
        }
    }

    /**
     *
     * @param numberOfRows is supposed to work as a breakpoint for how many players are shown
     * @return
     */
    public ArrayList<PlayerHighscore> getHighscores(int numberOfRows) {
        System.out.println("1");
        ArrayList<PlayerHighscore> highscores = new ArrayList<>();
        System.out.println("2");
        String sqlSelect = "SELECT * FROM Highscore2 ORDER BY Score DESC;";
            try {
                st = conn.createStatement();
                System.out.println("3");
                ResultSet res = st.executeQuery(sqlSelect);
                System.out.println("4");
                System.out.println("Start");
                while (res.next() && numberOfRows > 0) {
                    phs = new PlayerHighscore();
                    phs.setPlayerName(res.getString("Playername"));
                    phs.setPScore(res.getInt("Score"));
                    highscores.add(phs);
                    numberOfRows--;
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
            }
        return highscores;
    }


    /**
     * A method to create a table within the connected database.
     * is never used since the zip-file already contains a database
     * which is ready to use.
     */
    public void createSQLTable() {
        try {
            final String sqlCreateTable = "CREATE TABLE Highscore" +
                    "(LevelName      TEXT        NOT NULL," +
                    "PlayerName     TEXT        NOT NULL," +
                    "Time           INT         NOT NULL," +
                    "Score          INT         NOT NULL," +
                    "Credits         INT         NOT NULL)";
            st = conn.createStatement();
            st.executeUpdate(sqlCreateTable);
            st.close();
            conn.close();
            System.out.println("Created a table");
        } catch(SQLException e) {
            System.err.println("Failed to create table");
            System.err.println(e.getClass().getName() + ":" + e.getCause());
        }

    }

    /**
     * A method to close the connection to database.
     * @return
     */
    public boolean closeConnectionToDB(){
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }



}
