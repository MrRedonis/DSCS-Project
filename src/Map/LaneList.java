package Map;

import javafx.util.Pair;

import java.util.HashMap;

public class LaneList {
    private HashMap<Pair<Long,Long>, Lane> lanes = new HashMap<>();

    public void addLane(long first, long second, Lane lane){

        lanes.put(makePair(first,second),lane);
    }

    public Lane getLane(long first, long second){
        return lanes.get(makePair(first,second));
    }

    private Pair<Long,Long> makePair(long first, long second)
    {
        return new Pair<>(first,second);
    }

    public HashMap<Pair<Long, Long>, Lane> getLanes() {
        return lanes;
    }

    public LaneList addLaneList(LaneList ll){
        ll.getLanes().forEach(this.lanes::putIfAbsent);
        return this;
    }
}
