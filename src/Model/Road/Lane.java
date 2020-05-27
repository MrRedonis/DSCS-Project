package Model.Road;


//

public class Lane {
    private LaneSection[] route;
    private String label;
    boolean isInterconnected;

    public LaneSection getRoute(int index) {
        return route[index];

    }

    public Lane(LaneSection sections[], String label,boolean isInterconnected)
    {
        this.label=label;
        this.route=sections;
        this.isInterconnected=isInterconnected;
    }

    public void simulate() throws Exception {
        for (int i = 0; i <= route.length - 1; i++) {
            if (route[i] != null) {
                route[i].simulate();
                if(i!=route.length-1) {
                    if (!route[i].outOfSection.isEmpty() && route[i + 1] != null) {
                        route[i + 1].keepDriving(route[i].outOfSection.poll());
                    }
                }
                if(i==route.length-1&&this.isInterconnected&&!route[i].outOfSection.isEmpty())
                {
                    route[0].keepDriving(route[i].outOfSection.poll());
                }
            }
        }
    }

}
