package Map;

import Model.Car;
import javafx.util.Pair;

public class Cell {
    public static double measure=5;
    private int number;
    private int maxVelocity;
    private boolean occupied = false;
    private Pair<Long,Long> lane_id;
    private Car car;

    Cell(int maxVelocity,long begin, long end, int number){
        this.maxVelocity = maxVelocity;
        this.lane_id = new Pair<Long,Long> (begin,end);
        this.number = number;
    }

    public void setOccupied()
    {
        this.occupied=true;
    }

    public void setNotOccupied()
    {
        this.occupied=false;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean getOccupied()
    {
        return occupied;
    }

    public void occupyCell( Car car) throws Exception {
        if(!getOccupied()) {
            setOccupied();
            setCar(car);
        }
        else throw new Exception("Już zajęte");
    }
    void freeCell()
    {
        setNotOccupied();
        setCar(null);
    }
}