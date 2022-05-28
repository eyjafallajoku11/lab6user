package gorod;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x; //Значение поля должно быть больше -251
    private Double y; //Значение поля должно быть больше -310, Поле не может быть null
    public Coordinates(double iks, Double igr) {
        this.x = iks;
        this.y = igr;
    }

    public double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString(){
        return "("+x+","+ y +")";
    }
}
