import Model.*;
import Model.Road.Cell;
import Model.Road.Lane;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        //Car p1=
         Lane kopernika=new Lane(0,54,"Dworzec-Kopernika",false);

for(int i=16;i<26;i++)
    kopernika.get(i*2).occupyCell(new Car(i*3,70));

for(int i=0;i<10;i++) {
    kopernika.simulate();
}
kopernika.get(4);
	// write your code here
    }
}
