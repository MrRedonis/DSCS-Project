package Model.Road;

import Model.Car;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//ta klasa będzie aregować komorki w celu utworzenia odcinka jezdni;
//obiektem tej klasy będzie odcinek od do skrzyzowania, lica pawia-kopernika zajmuje 54 kratki (54*7.5 m)
// odcinki będą łączone w pasy (klasa lane)

public class LaneSection {
    ArrayList<Cell> lane;
    Boolean islimited;
    String label;
    Queue<Car> waitingCar;
    Queue<Car> outOfSection;



    public LaneSection(int from, int to, String label, boolean islimited) {
        this.lane = new ArrayList<>();
        this.waitingCar = new ArrayDeque<>();
        this.outOfSection=new ArrayDeque<>();
        this.islimited = islimited;
        this.label = label;
        for (int i = from; i < to; i++) {
            //lane.add(new Cell(i));
            lane.add(new Cell(i));
        }
    }
    public LaneSection(ArrayList<Cell> cells,String label, boolean islimited)
    {
        this.lane=cells;
        this.waitingCar=new ArrayDeque<>();
        this.outOfSection=new ArrayDeque<>();
        this.islimited = islimited;
        this.label = label;
    }

    public void simulate() throws Exception {
        Boolean cond = true;

        for (int i = lane.size() - 1; i >= 0; i--) //dla każdej komórki
        {
            if (lane.get(i).getOccupied()) {//jezeli jest zajeta
                lane.get(i).car.maxvelocity=lane.get(i).maxVelocity;
                cond = true;
                toMaxVelocity(i);
                while (cond) {
                    double plus = (lane.get(i).car.getVelocity()) / 18;// zmiana predkosci z km/h na kratki/s
                    if (plus < 1)//jezeli jest mniejsza niz 1 nie zmieniaj pozycji pojazdu; zakladamy minimalna predkosc 18 km/h
                        break;
                    if(i+(int)plus>(lane.size()-1)) { //pojazd wyjeżdża z sekcji
                        outOfSection.add(lane.get(i).car);
                        lane.get(i).freeCell();
                        cond=false;

                    }

                    else if (lane.get(i + (int) plus).getOccupied()) {//jezeli komorka do ktroej chce pojechac jest zajeta
                        lane.get(i).car.decreaseVelocity(18); //zmniejsz predkosc o 1 komorke/s

                    } else {//zmien komorke
                        lane.get(i + (int) plus).occupyCell(lane.get(i).car); //zajmij komorke
                        lane.get(i).freeCell();// zwolnij poprzednia
                        lane.get(i+(int)plus).car.setDistanceToNextCarInFront(toNextCar(i+(int)plus));

                        lane.get(i+(int)plus).car.maxvelocity=lane.get(i+(int)plus).maxVelocity;
                        cond = false;//nie powtarzaj
                    }

                }

            }
        }

        if (!waitingCar.isEmpty()) {
            if (!lane.get(0).getOccupied()) {
                addCar(waitingCar.poll());
            }
        }
    }

    public Cell get(int index) {
        return lane.get(index);
    }

    public void addCar(Car car) throws Exception {

        try {
            lane.get(0).occupyCell(car);
        } catch (Exception e) {
            waitingCar.add(car);
        }
    }

    public void keepDriving(Car car) throws Exception {

        try {
            lane.get(0).occupyCell(car);
        } catch (Exception e) {
            waitingCar.add(car);
        }
    }

    public void toMaxVelocity(int index) {
        if ((this.lane.get(index).car.getVelocity() + 18) <= this.lane.get(index).car.maxvelocity) {
            this.lane.get(index).car.increaseVelocity(18);
        }
        if((this.lane.get(index).car.getVelocity()>this.lane.get(index).car.maxvelocity))
        {
            this.lane.get(index).car.decreaseVelocity(18);
        }
    }

    public void print(PrintStream out) {
        //out.flush();
        for (int i = 0;i<this.lane.size()-1;i++) {
            if (this.lane.get(i).getOccupied()) out.println(i+"  X");
            else out.println(i+" ");
        }
    }
    public int toNextCar(int index)
    {
        int distance=0;
        if(index==lane.size()-1) return 20;
        for(int i=index+1;i<lane.size();i++)
        {
            if(lane.get(i).getOccupied())
                return distance+1;
            else ++distance;
            if(distance>=20)return 20;
        }
        return 20;
    }
}