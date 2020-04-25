package Model.Road;

import Model.Car;


//Cell to komórki, z których składa się droga, w przypadku pierwszej obwodnicy jest ona jednopasmowa i jedno lub dwukierunkowa
//Klasa Lane będzie składać się z
public class Cell {
    public boolean occupied; //czy komórka jest zajęta
    public Car car; //zamochód znajduje się na komórce
    public static double measure=7.5;
    public int number;

    public Cell(int number){
        setOccupied(false);
        this.number=number;
    }

    public void setOccupied(boolean occupied)
    {
        this.occupied=occupied;

    }
    public boolean getOccupied()
    {
        return occupied;
    }

    public void occupyCell( Car car)
    {
        this.occupied=true;
        this.car=car;
    }
    public  void freeCell()
    {
        occupied=false;
        car=null;
    }



}
