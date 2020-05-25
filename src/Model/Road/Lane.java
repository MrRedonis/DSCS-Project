package Model.Road;


//

public class Lane {
    private LaneSection[] route;
    private String label;


    public Lane(LaneSection sections[],String label)
    {
        this.label=label;
        this.route=sections;
    }

    public void simulate() throws Exception {
        for (int i = 0; i < route.length - 1; i++) {
            if (route[i] != null) {
                route[i].simulate();
                if (!route[i].outOfSection.isEmpty() && route[i + 1] != null) {
                    route[i + 1].keepDriving(route[i].outOfSection.poll());
                }
            }
        }
    }

}
