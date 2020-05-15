import Model.*;
import Model.Road.LaneSection;

public class Main {

    public static void main(String[] args) throws Exception {
        //Car p1=
        LaneSection kopernika = new LaneSection(0, 54, "Dworzec-Kopernika", false);

        for (int i = 1; i < 10; i++) {
            kopernika.addCar(new Car(27, 27));
            kopernika.addCar(new Car(54, 54));
            kopernika.simulate();
            kopernika.print(System.out);

        }

        for (int i = 1; i < 10; i++) {
            kopernika.addCar(new Car(54, 54));
            kopernika.simulate();
        }



    }
}


