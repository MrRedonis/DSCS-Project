package Model.Road;

import Model.Car;


//Cell to komórki, z których składa się droga, w przypadku pierwszej obwodnicy jest ona jednopasmowa i jedno lub dwukierunkowa
//
public class Cell {
    public boolean occupied; //czy komórka jest zajęta
    public Car car; //zamochód znajduje się na komórce

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



}
