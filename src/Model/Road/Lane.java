package Model.Road;


//

public class Lane {
    public LaneSection route[];


    public Lane(LaneSection sections[])
    {
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
