package Map;

import java.util.ArrayList;
import java.util.HashMap;

public class Way {
    private long id;
    private ArrayList<Long> knots = new ArrayList<Long>();
    private HashMap<String, String> tags;

    public void addKnot(long id){
        knots.add(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public KnotList buildRelations(KnotList knot_list){
        boolean is_oneway = false;
        if(tags.containsKey("maxspeed"))
        {
            if(tags.containsKey("oneway") && tags.get("oneway").equals("yes"))
            {
                is_oneway = true;
            }
        }

        for(int i=0;i<knots.size()-1;i++){
            knot_list.conncectKnots(knots.get(i),knots.get(i+1));
            if(!is_oneway){
                knot_list.conncectKnots(knots.get(i+1),knots.get(i));
            }
        }
        return knot_list;
    }

    public LaneList makeLines(KnotList knot_list){
        LaneList lane_list = new LaneList();
        boolean is_oneway = false;
        if(tags.containsKey("maxspeed"))
        {
            if(tags.containsKey("oneway") && tags.get("oneway").equals("yes"))
            {
                is_oneway = true;
            }
        }

        for(int i=0;i<knots.size()-1;i++){
            Lane lane = new Lane(knot_list.getKnot(knots.get(i)),knot_list.getKnot(knots.get(i+1)));
            if(tags.containsKey("maxspeed"))
            {
                lane.setMaxspeed(Integer.parseInt(tags.get("maxspeed")));
            }


            lane_list.addLane(knots.get(i),knots.get(i+1),lane);
            if(!is_oneway){
                Lane lane2 = new Lane(knot_list.getKnot(knots.get(i+1)),knot_list.getKnot(knots.get(i)));
                if(tags.containsKey("maxspeed"))
                {
                    lane.setMaxspeed(Integer.parseInt(tags.get("maxspeed")));
                }
                lane_list.addLane(knots.get(i+1),knots.get(i),lane2);
            }
        }
        return lane_list;
    }
}
