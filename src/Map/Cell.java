package Map;

import javafx.util.Pair;

public class Cell {
    public static double measure = 5;
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

    public void occupyCell( Car car)  {
        if(!getOccupied()) {
            setOccupied();
            setCar(car);
        }
    }
    void freeCell()
    {
        setNotOccupied();
        setCar(null);
    }

    public Car getCar() {
        return car;
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(int maxVelocity) {
        this.maxVelocity = maxVelocity;
    }
}