import Map.ParseOSM;
import Model.Car;
import Model.Road.Cell;
import Map.Lane;
//import Model.Road.Lane;
import Map.*;
import Model.Road.LaneSection;
import javafx.util.Pair;

import javax.swing.*;
//import java.awt.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class SimulatePanel extends JPanel {
    boolean cond=false;

    public void paintComponent(Graphics g) {
        System.out.println("Repaint");
        super.paintComponent(g);
        Random rand = new Random();

        Graphics2D g2 = (Graphics2D) g;
        int j=40;
        int k=0;

        for (Map.Entry<Pair<Long, Long>, Lane> entry : data.getLaneList().getLanes().entrySet()) {//to chyba źle wyciągam każda komórke
            for (int i = 0; i < entry.getValue().getCells().size() - 1; i++) {
                if(k>=800){j+=40;k=0;}
                if (entry.getValue().getCells().get(i).getOccupied()){
                    g2.setColor(entry.getValue().getCells().get(i).getCar().color);

                    g2.fillRect(i*20,j,20,40);
                    k++;
                }
                else{
                    g2.setColor(Color.darkGray);
                    g2.fillRect(i * 20, j, 20, 40);
                    k++;

                }



            }
        }
        if(cond) {
            g2.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g2.fillOval(400, 400, 30, 30);
        cond=!cond;
        }
        else{
            g2.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g2.fillRect(400, 400, 30, 30);
        }




        /*
        if(lane.getRoute(index)!=null) {




        for (int i = 0; i < lane.getRoute(index).getLane().size() - 1; i++) {
                if (lane.getRoute(index).getLane().get(i).getOccupied()) {
                    g2.setColor(lane.getRoute(index).getLane().get(i).car.color);
                    g2.fillRect(i * 20, 100, 20, 40);
                } else {
                    if (lane.getRoute(index).getLane().get(i).getMaxVelocity() == 18)
                        g2.setColor(Color.lightGray);
                    else if (lane.getRoute(index).getLane().get(i).getMaxVelocity() == 36)
                        g2.setColor(Color.GRAY);
                    else
                        g2.setColor(Color.darkGray);
                    g2.fillRect(i * 20, 100, 20, 40);
                }
            }
        }

        if(lane2.getRoute(index)!=null) {

        for (int i=lane2.getRoute(index).getLane().size()-1;i>=0;i--) {
            int j = lane2.getRoute(index).getLane().size() - 1 - i;
            if (lane2.getRoute(index).getLane().get(i).getOccupied()) {
                g2.setColor(lane2.getRoute(index).getLane().get(i).car.color);
                g2.fillRect(j * 20, 160, 20, 40);
            } else {
                if (lane2.getRoute(index).getLane().get(i).getMaxVelocity() == 18)
                    g2.setColor(Color.lightGray);
                else if (lane.getRoute(index).getLane().get(i).getMaxVelocity() == 36)
                    g2.setColor(Color.GRAY);
                else
                    g2.setColor(Color.darkGray);
                g2.fillRect(j * 20, 160, 20, 40);
            }

        }


        }

         */




    }


    Lane lane;
    Lane lane2;
    int index = 0;
    ParseOSM data = new ParseOSM();


    class AnimationThread extends Thread {
        boolean sus = true;

        public void pause() {
            sus = true;
        }

        public void nextSection() {
            index++;
        }

        public void previousSection() {
            if (index != 0)
                index--;
        }

        public void addCar() throws Exception {
            data.addCars(200);
             //  lane.getRoute(index).addCar(new Car());
              //lane2.getRoute(index).addCar(new Car());
        }

        public synchronized void stoppasue() {
            sus = false;
            notify();
        }

        public void run() {
            while (true) {
                try {
//                    lane2.simulate();
  //                  lane.simulate();
                    data.simulate();
                } catch (Exception e) {
                    System.out.println("Błąd");
                    e.printStackTrace();
                }

                repaint();
                synchronized (this) {
                    try {
                        if (sus) {
                            System.out.println("Suspending");
                            wait();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    void onStart() {
        System.out.println("Start or resume animation thread");
        // anim.start();
        anim.stoppasue();
    }

    void onNext() {
        System.out.println("Next section");
        anim.nextSection();
    }

    public void onPrevious() {
        System.out.println("Previous Section");
        anim.previousSection();
    }

    void onStop() {
        System.out.println("Suspend animation thread");
        anim.pause();
    }

    void onPlus() throws Exception {
        anim.addCar();
        System.out.println("Add car");
    }

    private AnimationThread anim = new AnimationThread();
    int height, width;

    SimulatePanel(int height, int width) throws Exception {

        ArrayList<Cell> test = new ArrayList<>();
        ArrayList<Cell> test5 = new ArrayList<>();
        ArrayList<Cell> test3 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            test.add(new Cell(i, 0, 0, 54));
            test3.add(new Cell(i, 0, 0, 72));
            test5.add(new Cell(i, 0, 0, 36));


        }
        for (int i = 0; i < 5; i++) {
            test5.add(new Cell(i, 0, 0, 0));
        }
        for (int i = 10; i < 25; i++) {
            test.add(new Cell(i, 0, 0, 18));
            test5.add(new Cell(i, 0, 0, 18));

        }
        for (int i = 10; i < 20; i++) {
            test.add(new Cell(i, 0, 0, 54));
            test5.add(new Cell(i, 0, 0, 54));
            test5.add(new Cell(i, 0, 0, 54));

        }

        for (int i = 10; i < 15; i++) {
            test.add(new Cell(i, 0, 0, 18));

        }
        for (int i = 10; i < 20; i++) {
            test.add(new Cell(i, 0, 0, 54));

        }


        LaneSection test2 = new LaneSection(test, "Test", false);
        LaneSection test4 = new LaneSection(test5, "test", false);


        int cars = 5;
         //   this.lane=new Lane(new LaneSection[]{test2,new LaneSection(test3,"Test",false)},"Test",true);
          //this.lane2=new Lane(new LaneSection[]{test4,new LaneSection(test3,"Test",false)},"Test",true);
        //for (int i = 0; i < cars; i++) {
            //      lane.getRoute(0).addCar(new Car());}

            this.height = height;
            this.width = width;
            anim.start();
            setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
        }


    }

