package Model.Road;


//

public class Lane {
    public LaneSection route[];
    public String label;


    public Lane(LaneSection sections[],String label)
    {
        this.label=label;
        this.route=sections;
    }

    public void simulate() throws Exception {
        for (LaneSection lane:route)
        {
            if(lane!=null)
            lane.simulate();
        }
    }

}
