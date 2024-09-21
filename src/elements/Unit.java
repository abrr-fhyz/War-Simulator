package src.elements;

import javafx.scene.paint.Color;

public class Unit {
    private double x;
    private double y;
    private Color color;
    private Color corp;
    private String unitName;
    private Commander unitCommander;
    private final int maxX = 540;
    private final int maxY = 625;
    private final int min = 10;

    public Unit(double x, double y, Color color, String unitName, Commander unitCommander, Color corp) {
        this.x = x;
        this.y = y;
        this.color = color;
        if(unitName.length() == 1)
            this.unitName = " " + unitName;
        else
            this.unitName = unitName;
        this.unitCommander = unitCommander;
        this.corp = corp;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = Math.min(x, maxX);
        this.x = Math.max(this.x, min);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = Math.min(y, maxY);
        this.y = Math.max(this.y, min);
    }

    public Color getColor() {
        return this.color;
    }

    public Color getCorp() {
        return this.corp;
    }

    public String getName() {
        return this.unitName;
    }

    public String getCommander() {
        return this.unitCommander.getData();
    }

    public void setCommander(Commander newCommander) {
        this.unitCommander = newCommander;
    }

    public int getCommanderRank() {
        return this.unitCommander.getRank();
    }

    public boolean contains(double x, double y) {
        return Math.abs(this.x - x) <= 5 && Math.abs(this.y - y) <= 5;
    }

    public String toString(){
        String unitString = this.x + "," + this.y + "," + this.color.toString();
        unitString += "," + this.unitName + "," + getCommander() + "," + this.corp.toString();
        return unitString;
    }
}