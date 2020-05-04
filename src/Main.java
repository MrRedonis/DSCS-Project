import Model.*;
import Model.Road.LaneSection;

public class Main {

    public static void main(String[] args) throws Exception {
        //Car p1=
        LaneSection kopernika = new LaneSection(0, 54, "Dworzec-Kopernika", false);

        for (int i = 1; i < 10; i++) {
            kopernika.addCar(new Car(27, 70));
            kopernika.simulate();
        }

        for (int i = 1; i < 2; i++) {
            kopernika.addCar(new Car(2*27, 70));
            kopernika.simulate();
        }



    }
}


