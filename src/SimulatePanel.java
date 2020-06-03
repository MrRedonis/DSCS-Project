import Model.Car;
import Model.Road.Cell;
import Model.Road.Lane;
import Model.Road.LaneSection;

import javax.swing.*;
//import java.awt.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SimulatePanel extends JPanel{

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Random rand=new Random();

        Graphics2D g2= (Graphics2D)g;

        for (int i=0;i<lane.getRoute(0).getLane().size()-1;i++)
        {
            if(lane.getRoute(0).getLane().get(i).getOccupied())
            {
                g2.setColor(lane.getRoute(0).getLane().get(i).car.color);
                g2.fillRect(i*20,20,20,40);
            }
            else{
                if(lane.getRoute(0).getLane().get(i).getMaxVelocity()==18)
                g2.setColor(Color.lightGray);
                else if (lane.getRoute(0).getLane().get(i).getMaxVelocity()==36)
                    g2.setColor(Color.GRAY);
                else
                    g2.setColor(Color.darkGray);
                g2.fillRect(i*20,20,20,40);
            }
        }


    }


    Lane lane;

    class AnimationThread extends Thread {
        boolean sus = true;

        public void pause() {
            sus = true;
        }

        public void addCar() throws Exception {
            lane.getRoute(0).addCar(new Car());
        }

        public synchronized void stoppasue() {
            sus = false;
            notify();
        }

        public void run() {
            while (true) {
                try {
                    lane.simulate();
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
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
    void onStart(){
        System.out.println("Start or resume animation thread");
        // anim.start();
        anim.stoppasue();
    }

    void onStop(){
        System.out.println("Suspend animation thread");
        anim.pause();
    }
    void onPlus() throws Exception {
        anim.addCar();
        System.out.println("Add car");
    }

    private AnimationThread anim=new AnimationThread();
    int height,width;
    SimulatePanel(int height,int width) throws Exception {

        ArrayList<Cell> test=new ArrayList<>();
        ArrayList<Cell>test3=new ArrayList<>();

        for (int i=0;i<10;i++) {
            test.add(new Cell(i,0,0,54));
            test3.add(new Cell(i,0,0,72));

        }
     /*   for (int i=10;i<25;i++) {
            test.add(new Cell(i,0,0,18));

        }
        for (int i=10;i<20;i++) {
            test.add(new Cell(i,0,0,54));

        }

        for (int i=10;i<15;i++) {
            test.add(new Cell(i,0,0,18));

        }
        for (int i=10;i<20;i++) {
            test.add(new Cell(i,0,0,54));

        }

      */

        LaneSection test2=new LaneSection(test,"Test",false);


int cars=0;
        this.lane=new Lane(new LaneSection[]{test2,new LaneSection(test3,"Test",false)},"Test",true);
        for (int i=0;i<cars;i++) {
            lane.getRoute(0).addCar(new Car());}

        this.height=height;
        this.width=width;
        anim.start();
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
    }




}
