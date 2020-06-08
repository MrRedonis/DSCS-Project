package Map;

import java.awt.*;
import java.util.Random;


//zgodnie z założniami, każdy pojazd ma jakiś cel podróży
public class Car {
    Random generayor = new Random();
    int velocity = 18;
    int init=generayor.nextInt(20);
    public Color color= new Color(generayor.nextInt(255),generayor.nextInt(255),generayor.nextInt(255));

    public int maxvelocity;//prędkość do której dąży pojazd
    private int numberOfSectionToPass = generayor.nextInt(20);// liczba komórek do przejechania

    //public Cell[][] neighbourhood;
    private int distanceToNextCarInFront = 0; // odległość od poprzedzającego samochodu; nie może być mniejsza od 0

    public Car(int velocity,int maxvelocity)
    {
        this.velocity=velocity;
        this.maxvelocity=maxvelocity;
    }
    public void decreaseSection(){
        numberOfSectionToPass--;
    }
    public int getNumberOfSectionToPass()
    {
        return numberOfSectionToPass;
    }

    public Car() {}

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

    public void setDistanceToNextCarInFront(int distance)
    {
        distanceToNextCarInFront=distance;
    }


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

    public void setMaxvelocity(int maxvelocity) {
        if(maxvelocity>=50&&maxvelocity<=60)
        this.maxvelocity = 54;
        else if(maxvelocity>=30&&maxvelocity<=40)
            this.maxvelocity=36;
    }
}
