package Map;

import java.util.ArrayList;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

public class Lane {
    private Knot begin;
    private Knot end;
    private double length;
    private int maxspeed;
    private int lanes = 1;
    private int cell_size = 5;
    private ArrayList<Cell> cells = new ArrayList<Cell>();
    private static double ROUND = 0.4; //Zaokrąglenie do liczby komórek


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

    public int getLanes() {
        return lanes;
    }

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
}
