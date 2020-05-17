package Map;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

public class Lane {
    private Knot begin;
    private Knot end;
    private double length;
    private int maxspeed;
    private int lanes;

    Lane(Knot begin, Knot end){
        this.begin = begin;
        this.end = end;
        this.length = geoToMeter();
    }

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
}
