package Map;

import java.util.HashMap;

public class Knot {
    private long id;
    private double lat;
    private double lon;
    private HashMap<String, String> tags;

    Knot(){}

    Knot(long id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    Knot(Knot knot, HashMap<String, String> tags){
        this.id = knot.id;
        this.lat = knot.lat;
        this.lon = knot.lon;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }
}
