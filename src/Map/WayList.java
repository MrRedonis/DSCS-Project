package Map;

import java.util.ArrayList;

public class WayList {
    private ArrayList<Way> ways = new ArrayList<>();

    public void addWay(Way way){
        ways.add(way);
    }
    
    public KnotList buildKnotsRelations(KnotList knot_list)
    {
        KnotList k_l = knot_list;
        for (Way way:this.ways) {
            k_l = way.buildRelations(k_l);
        }
        return k_l;
    }

    public LaneList buildLanes(KnotList knot_list){
        LaneList ll = new LaneList();
        for (Way way:this.ways) {
            ll = way.makeLines(knot_list);
        }
        return ll;
    }
}
