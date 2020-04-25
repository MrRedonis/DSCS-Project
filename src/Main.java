import Model.*;
import Model.Road.Cell;
import Model.Road.Lane;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Car p1=new Car(50,70);
        Lane kopernika=new Lane(0,54,"Dworzec-Kopernika",false);


        kopernika.get(4).occupyCell(p1);

        //ulica[10].occupyCell(p1);

	// write your code here
    }
}
