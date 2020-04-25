package Model;

import Model.Road.Cell;


//zgodnie z założniami, każdy pojazd ma jakiś cel podróży
public class Car {
    int velocity;
    public int maxvelocity;//prędkość do której dąży pojazd
    private int numberOfCellsToPass = 40;// liczba komórek do przejechania

    public Cell[][] neighbourhood;
    private int distanceToNextCarInFront = 0; // odległość od poprzedzającego samochodu; nie może być mniejsza od 0

    public Car(int velocity,int maxvelocity)
    {
        this.velocity=velocity;
        this.maxvelocity=maxvelocity;
    }

    public void decreaseVelocity(int velocityChange) // zgodnie z założeniami modelu, jezeli v+dv<0 to v=0
    {
        if(velocity-velocityChange<0)
            velocity=0;
        else
        velocity -= velocityChange;
    }

    public void increaseVelocity(int velocityChange) // zgodnie z założeniami modeli, jeżeli v+dv>maxvel to v=maxvel
    {
        if(velocity+velocityChange>=maxvelocity)
            velocity=maxvelocity;
        else
        velocity += velocityChange;
    }



    //--------------getery
    public int getVelocity()
    {
        return velocity;
    }
    public int getDistanceToNextCarInFront()
    {
        return distanceToNextCarInFront;
    }
    public int getMaxvelocity()
    {
        return maxvelocity;
    }


}
