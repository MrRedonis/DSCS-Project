package Map;

import java.util.ArrayList;
import java.util.HashMap;

public class KnotList {
    private HashMap<Long, ArrayList<Knot>> knots = new HashMap<>();

    public void addKnot(Knot knot){
        ArrayList<Knot> nodes= new ArrayList<>();
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
}
