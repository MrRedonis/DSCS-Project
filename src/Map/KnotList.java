package Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class KnotList {
    private HashMap<Long, ArrayList<Knot>> knots = new HashMap<>();

    public void addKnot(Knot knot){
        ArrayList<Knot> nodes = new ArrayList<>();
        nodes.add(knot);
        knots.put(knot.getId(),nodes);
    }

    public void conncectKnots(long first, long second){
        ArrayList<Knot> k_list = knots.get(first);
        k_list.add(knots.get(second).get(0));
        knots.replace(first,k_list);
    }

    public Knot getKnot(long id){
        return knots.get(id).get(0);
    }

    public ArrayList<Knot> getKnotNeighbors(long id){
        if(knots.get(id).size()!=1){
            ArrayList<Knot> neighbors = new ArrayList<>();
            for (int i=1;i<knots.get(id).size();i++){
                neighbors.add(knots.get(id).get(i));
            }
            return neighbors;
        }
        return null;
    }

    public Map<Long, ArrayList<Knot>> getKnotsAsHash() {
        return knots;
    }

    //Lista sąsiedztwa węzłów
    public ArrayList<ArrayList<Knot>> getKnotsAsArray(){
        ArrayList<ArrayList<Knot>> array_knots = new ArrayList<>();
        for(Map.Entry<Long, ArrayList<Knot>> entry : knots.entrySet()) {
            array_knots.add(entry.getValue());
        }
        return array_knots;
    }

    //Zwraca losowy węzeł końcowy - posiadający tylko jednego sąsiada
    public Knot getRandomTerminalKnot(){
        ArrayList<Knot> array_knots = new ArrayList<>();
        for(Map.Entry<Long, ArrayList<Knot>> entry : knots.entrySet()) {
            if(entry.getValue().size()==2)
            array_knots.add(entry.getValue().get(0));
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, array_knots.size() + 1);
        return array_knots.get(randomNum);
    }

    //Zwraca losowy węzeł sąsiadujący z podanym jako argument
    public Knot getRandomNextKnot(Knot knot){
        int randomNum = ThreadLocalRandom.current().nextInt(1, knots.get(knot.getId()).size() + 1);
        return knots.get(knot.getId()).get(randomNum);
    }

    public Long getRandomNextKnotId(long id){//Zwróc id sąsiedniego węzła który nie jest węzłem id, jeśli nie ma takiego to zwróc id
        int randomNum;
        if(knots.get(id).size()>1){
            randomNum = ThreadLocalRandom.current().nextInt(1, knots.get(id).size());
            return knots.get(id).get(randomNum).getId();
        }
        else return id;
    }
}
