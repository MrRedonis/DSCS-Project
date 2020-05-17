package Map;

import javafx.util.Pair;

import java.util.HashMap;

public class LaneList {
    private HashMap<Pair<Long,Long>, Lane> lanes = new HashMap<>();

    public void addLane(long first, long second, Lane lane){
        Pair<Long,Long> pair = new Pair<>(first,second);
        lanes.put(pair,lane);
    }

    public Lane getLane(int first, int second){
        Pair<Integer,Integer> pair = new Pair<>(first,second);
        return lanes.get(pair);
    }
}
