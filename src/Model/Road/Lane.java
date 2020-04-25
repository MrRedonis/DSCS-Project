package Model.Road;

import java.util.ArrayList;
import java.util.List;

//wszytkie pasy są jednokierunkowe; będzie je później układać w drogę

public class Lane {
    ArrayList<Cell> lane;
    Boolean islimited;
    String label;

    public Lane(int from, int to, String label, boolean islimited)
    {
        this.lane=new ArrayList<>();
        this.islimited=islimited;
        this.label=label;
        for(int i=from;i<to;i++) {
            //lane.add(new Cell(i));
            lane.add(new Cell(i));
        }
    }
    public Cell get(int index)
    {
        return lane.get(index);
    }



}
