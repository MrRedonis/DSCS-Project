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
        /*
        for(int i=0;i<20;i++) {
            if(i%2==0) {
                g2.setColor(Color.GRAY);

                g2.fillRect(i * 10, 40, 40, 10);
            }
            else {
                g2.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                g2.fillRect(i * 10, 40, 40, 10);
            }
        }

         */
        for (int i=0;i<lane.getRoute(0).getLane().size()-1;i++)
        {
            if(lane.getRoute(0).getLane().get(i).getOccupied())
            {
                g2.setColor(lane.getRoute(0).getLane().get(i).car.color);
                g2.fillRect(i*20,i*20,20,20);
            }
            else{
                g2.setColor(Color.GRAY);
                g2.fillRect(i*20,i*20,20,20);
            }
        }


    }


    Lane lane;

    class AnimationThread extends Thread {
        boolean sus = false;

        public void pause() {
            sus = true;
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
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
    private AnimationThread anim=new AnimationThread();
    int height,width;
    SimulatePanel(int height,int width) throws Exception {

        ArrayList<Cell> test=new ArrayList<>();

        for (int i=0;i<10;i++) {
            test.add(new Cell(i,0,0,54));

        }
        for (int i=10;i<20;i++) {
            test.add(new Cell(i,0,0,18));

        }
        for (int i=10;i<20;i++) {
            test.add(new Cell(i,0,0,36));

        }
        for (int i=10;i<20;i++) {
            test.add(new Cell(i,0,0,54));

        }

        LaneSection test2=new LaneSection(test,"Test",false);


        this.lane=new Lane(new LaneSection[]{test2,null},"Test",true);
        for (int i=0;i<30;i++) {
            lane.getRoute(0).addCar(new Car());}
        this.height=height;
        this.width=width;
        anim.start();
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
    }




}
