package gameenvironment;

import gamecomponents.Rules;

import gamecomponents.TowerRules;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Created by rasblo on 2016-12-01.
 */
public class LoadLevel {
    private String XMLpath;
    private Document doc;
    private ArrayList<char[][]> levelMatrix;
    private ArrayList<Rules> rulesList;
    private ArrayList<String> levelNames;

    public LoadLevel(String customPath) throws IOException{
        this.XMLpath = (customPath.length() > 0) ? customPath : "levels.xml";;
        this.levelMatrix = new ArrayList<>();
        this.rulesList = new ArrayList<>();
        this.levelNames = new ArrayList<>();
        try {
            String schemaLang = "http://www.w3.org/2001/XMLSchema";
            SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLang);
            Schema schema = schemaFactory.newSchema(new StreamSource("levels.xsd"));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setSchema(schema); //add validation using the schema

            DocumentBuilder parser = dbFactory.newDocumentBuilder();

            this.doc = parser.parse(new InputSource(this.XMLpath));

            //Fixa så att tex inte flera textnoder finns efter varandra
        } catch (ParserConfigurationException e) {
            throw new IOException("Unable to configure parser");
        } catch (SAXException e) {
            throw new IOException("Not correct format");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found");
        }

        NodeList levels = this.doc.getElementsByTagName("level");
        for(int i = 0; i < levels.getLength(); i++) {
            Node currentLevel = levels.item(i);
            this.levelNames.add(currentLevel.getAttributes().getNamedItem("name").getTextContent());

            if(currentLevel.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element)currentLevel;
                this.setLevelMatrix(e);
                this.setRules(e);
            }
        }
    }

    public char[][] getLevel(int levelNumber){
        return this.levelMatrix.get(levelNumber-1);
    }

    public Rules getRules(int levelNumber){
        return this.rulesList.get(levelNumber-1);
    }

    public ArrayList<String> getLevelNames(){
        return this.levelNames;
    }

    private void setLevelMatrix(Element level){
        NodeList rows = level.getElementsByTagName("row");
        char[][] tempMatrix = new char[rows.item(0).getTextContent().length()][rows.getLength()];
        for(int rowIterator = 0; rowIterator < rows.getLength(); rowIterator++){
            Node currentRow = rows.item(rowIterator);
            String rowString = currentRow.getTextContent();
            for(int stringIterator = 0; stringIterator < rowString.length(); stringIterator++){
                tempMatrix[stringIterator][rowIterator] = rowString.charAt(stringIterator);
            }
        }
        this.levelMatrix.add(tempMatrix);
    }

    private void setRules(Element level){
        Rules tempRules = new Rules();

        int temp = Integer.parseInt(level.getElementsByTagName("creditsInitial").item(0).getTextContent());
        tempRules.setCreditsAtStart(temp);

        temp = Integer.parseInt(level.getElementsByTagName("creditsOnGoal").item(0).getTextContent());
        tempRules.setCreditsAtCompletion(temp);

        temp = Integer.parseInt(level.getElementsByTagName("goalAmount").item(0).getTextContent());
        tempRules.setNumberOfUnitsToReachGoal(temp);

        temp = Integer.parseInt(level.getElementsByTagName("towerAmount").item(0).getTextContent());
        tempRules.setNumberOfTowers(temp);

        this.setTowerRules(tempRules, level);
        this.setTroopRules(tempRules, level);

        this.rulesList.add(tempRules);
    }

    private void setTowerRules(Rules rules, Element level){
        NodeList towers = level.getElementsByTagName("tower");
        for(int i = 0; i < towers.getLength(); i++){
            Node currentTower = towers.item(i);

            if(currentTower.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element)currentTower;
                String name = e.getElementsByTagName("name").item(0).getTextContent();
                int damage = Integer.parseInt(e.getElementsByTagName("damage").item(0).getTextContent());
                int coolDown = Integer.parseInt(e.getElementsByTagName("coolDown").item(0).getTextContent());
                int rangeWidth = Integer.parseInt(e.getElementsByTagName("rangeWidth").item(0).getTextContent());
                int rangeHeight  = Integer.parseInt(e.getElementsByTagName("rangeHeight").item(0).getTextContent());

                rules.addTowerRules(name, damage, coolDown, rangeWidth, rangeHeight);
            }
        }
    }

    private void setTroopRules(Rules rules, Element level){
        NodeList troops = level.getElementsByTagName("troop");
        for(int i = 0; i < troops.getLength(); i++){
            Node currentTroop = troops.item(i);

            if(currentTroop.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element)currentTroop;
                String name = e.getElementsByTagName("name").item(0).getTextContent();
                int health = Integer.parseInt(e.getElementsByTagName("health").item(0).getTextContent());
                int speed = Integer.parseInt(e.getElementsByTagName("speed").item(0).getTextContent());
                int cost  = Integer.parseInt(e.getElementsByTagName("cost").item(0).getTextContent());

                rules.addTroopRules(name, health, speed, cost);
            }
        }
    }
}
