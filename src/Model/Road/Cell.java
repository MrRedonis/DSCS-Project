package Model.Road;

import Model.Car;


//Cell to komórki, z których składa się droga, w przypadku pierwszej obwodnicy jest ona jednopasmowa i jedno lub dwukierunkowa
//Klasa Lane będzie składać się z
public class Cell {
    private boolean occupied; //czy komórka jest zajęta
    public Car car; //zamochód znajduje się na komórce
    public static double measure=5; //wielkosc komorki
    private int number;
    int maxVelocity=54;
    private double x;
    private double y;

    Cell(int number){
        setOccupied(false);
        this.number=number;
    }
    public Cell(int number, double x, double y)
    {
        setOccupied(false);
        this.number=number;
        this.x=x;
        this.y=y;
    }
    public Cell(int number, double x, double y,int maxVelocity)
    {
        setOccupied(false);
        this.number=number;
        this.maxVelocity=maxVelocity;
        this.x=x;
        this.y=y;
    }

    private void setOccupied(boolean occupied)
    {
        this.occupied=occupied;

    }
   public boolean getOccupied()
    {
        return occupied;
    }

    public void occupyCell( Car car) throws Exception {
        if(!occupied) {
            this.occupied = true;
            this.car = car;
        }
        else throw new Exception("Już zajęte");
    }
    void freeCell()
    {
        occupied=false;
        car=null;
    }

    public int getMaxVelocity(){
        return maxVelocity;
    }



}
