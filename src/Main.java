import Model.Car;
import Model.Road.Cell;
import Model.Road.Lane;
import Model.Road.LaneSection;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        int sim=40;
        LaneSection kopernika = new LaneSection(54, 108, "Dworzec-Kopernika", false);
        LaneSection dworzec = new LaneSection(0, 54, "Globus-Dworzec", false);
        LaneSection zyblikiewicza = new LaneSection(108, 120, "Kopernika-Zyblikiewicza", false);

//        LaneSection test=new LaneSection(new ArrayList<Cell>())
        ArrayList<Cell>test=new ArrayList<>();
        for (int i=0;i<10;i++) {
            test.add(new Cell(i,0,0,54));

        }
        for (int i=10;i<20;i++) {
            test.add(new Cell(i,0,0,18));

        }
        LaneSection test2=new LaneSection(test,"Test",false);
        Lane ulica=new Lane(new LaneSection[]{test2, zyblikiewicza},"Test",true);

        for (int i=0;i<30;i++) {
            ulica.getRoute(0).addCar(new Car());
            ulica.simulate();
        }

        for (int i=0;i<sim;i++) {
        //ulica.getRoute(0).addCar(new Car());
        ulica.simulate();
          }

    }
}


