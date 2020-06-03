package Map;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

public class Lane {
    private Knot begin;
    private Knot end;
    private double length;
    private int maxspeed;
    private int cell_size = 5;
    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private static double ROUND = 0.4; //Zaokrąglenie do liczby komórek

    public Queue<Car> waitingCar = new ArrayDeque<>();
    public Queue<Car> outOfSection = new ArrayDeque<>();


    Lane(Knot begin, Knot end){
        this.begin = begin;
        this.end = end;
        this.length = geoToMeter();
        makeCells();
    }

    public Lane() {}

    public int getMaxspeed() {
        return maxspeed;
    }

    public int maxspeed(){
        return maxspeed;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }

    //Przelczanie odległości geograficznych na odległości w metrach.
    private double geoToMeter(){
        return sqrt(pow(begin.getLat()- end.getLat(),2)+pow(cos((end.getLat()*PI)/180)*(begin.getLon()- end.getLon()),2))*111321.4;
    }

    public double getLength() {
        return length;
    }

    /*public int getLanes() {
        return lanes;
    }*/

    private void makeCells()
    {
        int count = 0;
        if(length/cell_size<1) count = 1;
        else{
            count = (int) length/cell_size;
            if(length/cell_size - count > ROUND) count++;
        }
        for (int i=0;i<count;i++)
        {
            cells.add(new Cell(maxspeed,begin.getId(),end.getId(),i));
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public int getCell_size() {
        return cell_size;
    }

    public Cell getCell(int index){
        return cells.get(index);
    }

    //###################################################################################

    public void simulate() throws Exception {
        boolean cond = true;

        for (int i = cells.size() - 1; i >= 0; i--) //dla każdej komórki
        {
            if (cells.get(i).getOccupied()) {//jezeli jest zajeta

                cells.get(i).getCar().setMaxvelocity(maxspeed);

                cond = true;
                toMaxVelocity(i);
                // if(lane.get(i).car.getDistanceToNextCarInFront()<3)lane.get(i).car.decreaseVelocity(18);
                while (cond) {
                    double plus = (double) (cells.get(i).getCar().getVelocity()) / 18;// zmiana predkosci z km/h na kratki/s
                    if (plus < 1)//jezeli jest mniejsza niz 1 nie zmieniaj pozycji pojazdu; zakladamy minimalna predkosc 18 km/h
                        break;
                    if(i+(int)plus>(cells.size()-1)) { //pojazd wyjeżdża z sekcji
                        cells.get(i).getCar().decreaseSection();
                        //   if(lane.get(i).car.getNumberOfSectionToPass()==0)
                        // {
                        //lane.get(i).freeCell();
                        //}
                        //else {
                        if(cells.get(i).getCar().getNumberOfSectionToPass()!=0)
                            outOfSection.add(cells.get(i).getCar());
                        cells.get(i).freeCell();
                        cond = false;
                        //}
                    }

                    else if (cells.get(i + (int) plus).getOccupied()) {//jezeli komorka do ktroej chce pojechac jest zajeta
                        cells.get(i).getCar().decreaseVelocity(18); //zmniejsz predkosc o 1 komorke/s

                    } else {//zmien komorke

                        cells.get(i + (int) plus).occupyCell(cells.get(i).getCar()); //zajmij komorke
                        cells.get(i).freeCell();// zwolnij poprzednia
                        cells.get(i+(int)plus).getCar().setDistanceToNextCarInFront(toNextCar(i+(int)plus));

                        cells.get(i+(int)plus).getCar().setMaxvelocity(maxspeed);
                        cond = false;//nie powtarzaj
                    }

                }

            }
        }

        if (!waitingCar.isEmpty()) { //Jeśli kolejka nie jest pusta
            if (!cells.get(0).getOccupied()) {//Jeśli pierwsza komórka nie jest zajęta
                addCar(waitingCar.poll());
            }
        }
    }

    /*public Model.Road.Cell get(int index) {
        return lane.get(index);
    }*/

    public void addCar() {
        addCar(new Car(0,maxspeed));
    }

    /*public void addCar(Car car) throws Exception {

        try {
            cells.get(0).occupyCell(car);
        } catch (Exception e) {
            waitingCar.add(car);
        }
    }*/

    public void addCar(Car car) {
        if(cells.get(0).getOccupied()){
            this.waitingCar.add(car);
        }
        else {
            cells.get(0).occupyCell(car);
        }
    }

    public void keepDriving(Car car) throws Exception {
        addCar(car);
    }

    public void toMaxVelocity(int index) {
        if ((this.cells.get(index).getCar().getVelocity() + 18) <= this.cells.get(index).getCar().maxvelocity) {
            this.cells.get(index).getCar().increaseVelocity(18);
        }
        if((this.cells.get(index).getCar().getVelocity()>this.cells.get(index).getCar().maxvelocity))
        {
            this.cells.get(index).getCar().decreaseVelocity(18);
        }
    }

    public void print(PrintStream out) {
        //out.flush();
        for (int i = 0;i<this.cells.size()-1;i++) {
            if (this.cells.get(i).getOccupied()) out.println(i+"  X");
            else out.println(i+" ");
        }
    }
    public int toNextCar(int index)
    {
        int distance=0;
        if(index==cells.size()-1) return 20;
        for(int i=index+1;i<cells.size();i++)
        {
            if(cells.get(i).getOccupied())
                return distance;
            else ++distance;
            if(distance>=20)return 20;
        }
        return 20;
    }

    public Knot getEnd() {
        return end;
    }
}
