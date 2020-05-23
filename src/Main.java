import Model.*;
import Model.Road.Lane;
import Model.Road.LaneSection;

import javax.lang.model.type.NullType;

public class Main {

    public static void main(String[] args) throws Exception {
        //Car p1=
        LaneSection kopernika = new LaneSection(54, 108, "Dworzec-Kopernika", false);
        LaneSection dworzec = new LaneSection(0, 54, "Globus-Dworzec", false);

        for (int i = 1; i < 10; i++) {
            kopernika.addCar(new Car(18, 18));
            kopernika.addCar(new Car(54, 54));
        }

        for (int i = 1; i < 10; i++) {
            kopernika.addCar(new Car(54, 54));

        }
       // dworzec.
        dworzec.get(53).occupyCell(new Car(18,18));
        LaneSection[] ulica={dworzec, null,kopernika,null};

        Lane pas=new Lane(ulica,"ulica");
        for (int i=0;i<10;i++)
        pas.simulate();



    }
}


