package Model.Road;


//

import java.util.ArrayList;

public class Lane {
    private LaneSection[] route;
    private String label;
    boolean isInterconnected;
    public int size(){
        return route.length;
    }

    public LaneSection getRoute(int index) {
        if(route[index]==null)return null;
        if(route.length-1<index)
            return null;
        if(route[index].lane.size()==0) return null;
        return route[index];

    }

    public Lane(LaneSection sections[], String label,boolean isInterconnected)
    {
        this.label=label;
        this.route=sections;
        this.isInterconnected=isInterconnected;
    }
    public Lane(int[] distance,int[] velocity,String label,boolean isInterconnected) throws Exception {
        this.label=label;
        this.isInterconnected=isInterconnected;
        LaneSection[] laneSections=new LaneSection[distance.length];
        if(distance.length!=velocity.length) throw new Exception("Błąd przy tworzeniu Lane (tablice nierównej długości");
        else
        {

            for(int i=0;i<distance.length-1;i++)
            {
                if(distance[i]==0)
                {
                    laneSections[i]=null;
                }
                ArrayList<Cell>cells=new ArrayList<>();

                for(int j=0;j<distance[i];j++)
                {
                    cells.add(new Cell(j,0,0,velocity[i]));
                }
                laneSections[i]=new LaneSection(cells,"test",false);

            }
        }
        this.route=laneSections;


    }

    public void simulate() throws Exception {
        for (int i = 0; i <= route.length - 1; i++) {
            if (route[i] != null) {
                route[i].simulate();
                if(i!=route.length-1) {
                    if (!route[i].outOfSection.isEmpty() && route[i + 1] != null) {
                        route[i + 1].keepDriving(route[i].outOfSection.poll());
                    }
                    else if (!route[i].outOfSection.isEmpty() && route[i+1]==null)route[i].outOfSection.poll();
                }
                if(i==route.length-1&&this.isInterconnected&&!route[i].outOfSection.isEmpty())
                {
                    route[0].keepDriving(route[i].outOfSection.poll());
                }
            }
        }
    }

}
