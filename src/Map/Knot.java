package Map;

import java.util.HashMap;

//Węzeł - reprezentacja noda z osm

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

    //Pobierz id więzła
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

    //Czy węzeł jest przejściem?
    public boolean isHighwayCrossing(){
        if(tags.containsKey("highway")){
            return tags.get("highway").equals("crossing");
        }
        return false;
    }

    //Czy jest w tym miejscu sygnalizacja świetlna?
    public boolean isTrafficSignal(){
        if(tags.containsKey("highway")){
            return tags.get("highway").equals("traffic_signals");
        }
        return false;
    }

    //Czy jest tu próg zwalniający?
    public boolean isTrafficCalmingTable(){
        if(tags.containsKey("traffic_calming")){
            return tags.get("traffic_calming").equals("table");
        }
        return false;
    }
}
