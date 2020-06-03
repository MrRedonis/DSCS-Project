package Map;

import java.util.concurrent.TimeUnit;

public class MapBuild {
    public static void main(String[] args) throws Exception {
        ParseOSM data = new ParseOSM();
        data.addCars(1000); //Dodanie numCars aut do symulacji
        for(int i=0;i<20;i++){
            data.simulate();
            TimeUnit.SECONDS.sleep(1);
            //System.out.print(i+" ");
        }
    }
}