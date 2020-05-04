package Model.Road;

import Model.Car;

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

    public LaneSection(int from, int to, String label, boolean islimited)
    {
        this.lane=new ArrayList<>();
        this.waitingCar=new ArrayDeque<>();
        this.islimited=islimited;
        this.label=label;
        for(int i=from;i<to;i++) {
            //lane.add(new Cell(i));
            lane.add(new Cell(i));
        }
    }
    public void simulate() throws Exception {
        Boolean cond=true;
        for(int i=lane.size()-1;i>=0;i--) //dla każdej komórki
        {

            if ( lane.get(i).getOccupied()) {//jezeli jest zajeta
                while (cond) {
                    double plus = (lane.get(i).car.getVelocity()) / 27;// zmiana predkosci z km/h na kratki/s
                    if (plus < 1)//jezeli jest mniejsza niz 1 nie zmieniaj pozycji pojazdu; zakladamy minimalna predkosc 27 km/h
                        break;
                    if (lane.get(i + (int) plus).getOccupied()) {//jezeli komorka do ktroej chce pojechac jest zajeta
                        lane.get(i).car.decreaseVelocity(27); //zmniejsz predkosc o 1 komorke/s

                    } else {
                        lane.get(i + (int) plus).occupyCell(lane.get(i).car); //zajmij komorke
                        lane.get(i).freeCell();// zwolnij poprzednia
                        cond=false;//nie powtarzaj
                    }
                }
            }
        }
        if(!waitingCar.isEmpty())
        {
            if(!lane.get(0).getOccupied())
            {
                addCar(waitingCar.poll());
            }
        }
    }
    public Cell get(int index)
    {
        return lane.get(index);
    }

    public void addCar(Car car) throws Exception {

        try {
            lane.get(0).occupyCell(car);
        } catch (Exception e) {
            waitingCar.add(car);
        }
    }




}
