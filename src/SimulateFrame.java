import Model.Car;
import Model.Road.Cell;
import Model.Road.Lane;
import Model.Road.LaneSection;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class SimulateFrame extends JFrame {
    Lane lane;

    SimulateFrame(Lane lane) throws Exception {
        super("Simulate");
        this.lane = lane;

        buildGui();
    }

    private SimulatePanel simpanel = new SimulatePanel(700, 630);

    void buildGui() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel opt = new JPanel();
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton plus = new JButton("Plus");
        plus.addActionListener(p-> {
            try {
                simpanel.onPlus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        opt.add(plus);


        start.addActionListener(p -> simpanel.onStart());


        start.addActionListener(p -> { start.setEnabled(false);stop.setEnabled(true); });
        opt.add(start);

        stop.addActionListener(p->simpanel.onStop());
        stop.setEnabled(false);

        stop.addActionListener(p->{stop.setEnabled(false);start.setEnabled(true);});

        opt.add(stop);

        panel.add(opt, BorderLayout.NORTH);
        panel.add(simpanel, BorderLayout.CENTER);
        setContentPane(panel);


    }


    public static void main(String[] args) throws Exception {
        int sim=40;
        LaneSection kopernika = new LaneSection(54, 108, "Dworzec-Kopernika", false);
        LaneSection dworzec = new LaneSection(0, 54, "Globus-Dworzec", false);
        LaneSection zyblikiewicza = new LaneSection(108, 120, "Kopernika-Zyblikiewicza", false);



        ArrayList<Cell> test=new ArrayList<>();
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
           // ulica.simulate();
        }

        for (int i=0;i<sim;i++) {
            //ulica.getRoute(0).addCar(new Car());
            ulica.simulate();
        }

        {
            SimulateFrame frame = new SimulateFrame(ulica);
            frame.setSize(900, 900);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.setVisible(true);
        }
    }
}