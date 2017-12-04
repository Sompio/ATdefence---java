package gameenvironment;

import gamecomponents.troops.Direction;
import gameenvironment.environment.Area;
import gameenvironment.environment.Road;
import gameenvironment.environment.Crossroad;
import gamecomponents.Rules;

import java.awt.*;
import java.util.ArrayList;
import java.lang.reflect.*;


/**
 * Created by rasblo on 2016-12-01.
 */
public class Level {
    private Area[][] matrix;
    private Rules rules;
    private Position startPos;
    private Position goalPos;
    private ArrayList<Position> towerZones;
    private Position[] towerPos;
    private ArrayList<Position> crossroadPositions;
    private ArrayList<Crossroad> crossroads;
    private String name;

    public Level(char[][] charMatrix, Rules rules, String name) throws ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        this.rules = rules;
        this.name = name;
        this.towerZones = new ArrayList<>();
        this.crossroadPositions = new ArrayList<>();
        this.crossroads = new ArrayList<>();
        this.instantiateMatrix(charMatrix);

        this.towerPos = this.randomizeTowerPositions();
        this.crossroadDirections();
    }

    public String getName() {
        return name;
    }

    public int getWidth(){
        return this.matrix.length;
    }

    public int getHeight(){
        return this.matrix[0].length;
    }

    public void instantiateMatrix(char[][] charMatrix) throws ClassNotFoundException, InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.matrix = new Area[charMatrix.length][charMatrix[0].length];
        for(int i = 0; i < charMatrix.length; i++){
            for(int j = 0; j < charMatrix[0].length; j++){
                try {
                    Class<?> areaClass = Class.forName("gameenvironment.environment."+this.symbolToClass(charMatrix[i][j]));
                    Constructor con = areaClass.getConstructor(Position.class);

                    Area areaObject = (Area)con.newInstance(new Position(i*40, j*40));
                    this.matrix[i][j] = areaObject;

                    if(charMatrix[i][j] == 's') {
                        this.setStartPos(new Position(i*40, j*40));
                    }
                    else if(charMatrix[i][j] == 'g'){
                        this.setGoalPos(new Position(i*40, j*40));
                    }
                    else if(charMatrix[i][j] == 'z'){
                        this.towerZones.add(new Position(i*40, j*40));
                    }
                    else if(charMatrix[i][j] == 'c'){
                        this.crossroadPositions.add(new Position(i, j));
                        this.crossroads.add((Crossroad)areaObject);
                    }
                }
                catch (ClassNotFoundException | InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw e;
                }
            }
        }
    }

    public Area[][] getLevelMatrix(){
        return this.matrix;
    }

    public Position[] getTowerPositions(){
        return this.towerPos;
    }

    public ArrayList<Crossroad> getCrossroads(){
        return this.crossroads;
    }

    private Position[] randomizeTowerPositions(){
        Position[] towerPos = new Position[this.rules.getNumberOfTowers()];
        ArrayList<Position> tempZones = new ArrayList<>();
        for(Position p : this.towerZones){
            tempZones.add(new Position(p.getX(), p.getY()));
            System.out.println("*");
        }
        for(int i = 0; i < this.rules.getNumberOfTowers(); i++){
            Position possiblePosition = tempZones.get((int)Math.floor(Math.random()*tempZones.size()));
            towerPos[i] = possiblePosition;

            tempZones.remove(possiblePosition);
        }
        return towerPos;
    }

    private void crossroadDirections(){
        for(int crossroad = 0; crossroad < this.crossroadPositions.size(); crossroad++){
            Position p = this.crossroadPositions.get(crossroad);
            if(this.withinBounds(p.getX(), p.getY()-1) && this.matrix[p.getX()][p.getY()-1] instanceof Road){
                Crossroad c = (Crossroad)this.matrix[p.getX()][p.getY()];
                c.addDirection(Direction.NORTH);
            }
            if(this.withinBounds(p.getX()+1, p.getY()) && this.matrix[p.getX()+1][p.getY()] instanceof Road){
                Crossroad c = (Crossroad)this.matrix[p.getX()][p.getY()];
                c.addDirection(Direction.EAST);
            }
            if(this.withinBounds(p.getX(), p.getY()+1) && this.matrix[p.getX()][p.getY()+1] instanceof Road){
                Crossroad c = (Crossroad)this.matrix[p.getX()][p.getY()];
                c.addDirection(Direction.SOUTH);
            }
            if(this.withinBounds(p.getX()-1, p.getY()) && this.matrix[p.getX()-1][p.getY()] instanceof Road){
                Crossroad c = (Crossroad)this.matrix[p.getX()][p.getY()];
                c.addDirection(Direction.WEST);
            }
        }
    }

    public Rules getRules(){
        return this.rules;
    }

    public Position getStartPos(){
        return new Position(startPos.getX(), startPos.getY());
    }

    public void setStartPos(Position pos){
        this.startPos = pos;
    }

    public Position getGoalPos(){
        return this.goalPos;
    }

    public void setGoalPos(Position pos){
        this.goalPos = pos;
    }

    public void draw(Graphics graphic){
        for(int x = 0; x < this.getWidth(); x++){
            for(int y = 0; y < this.getHeight(); y++){
                this.matrix[x][y].draw(graphic);
            }
        }
    }

    private boolean withinBounds(int x, int y){
        if((x >= 0 && x < this.getWidth()) &&
                (y >= 0 && y < this.getHeight())){
            return true;
        }
        return false;
    }

    private String symbolToClass(char symbol){
        switch(symbol){
            case 't':
                return "Snow";
            case 'r':
                return "Road";
            case 'z':
                return "Snow";
            case 'c':
                return "Crossroad";
            case 'g':
                return "Road";
            case 's':
                return "Road";
            default:
                return "";
        }
    }
}
