import Model.Car;
import Model.Road.Lane;
import Model.Road.LaneSection;

public class Main {

    public static void main(String[] args) throws Exception {
        int sim=40;
        LaneSection kopernika = new LaneSection(54, 108, "Dworzec-Kopernika", false);
        LaneSection dworzec = new LaneSection(0, 54, "Globus-Dworzec", false);
        LaneSection zyblikiewicza = new LaneSection(108, 120, "Kopernika-Zyblikiewicza", false);


        for (int i = 1; i < 10; i++) {
            kopernika.addCar(new Car(18, 18));
            kopernika.addCar(new Car(54, 54));
        }

        for (int i = 1; i < 10; i++) {
            kopernika.addCar(new Car(54, 54));

        }
       // dworzec.
        dworzec.get(53).occupyCell(new Car(18,18));


        Lane pas=new Lane(new LaneSection[]{
                dworzec,
                null,
                kopernika,
                zyblikiewicza},"ulica");
        for (int i=0;i<sim;i++)
        pas.simulate();

//kopernika.print(System.out);
pas.simulate();



    }
}


