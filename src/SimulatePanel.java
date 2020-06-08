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
        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(index),100,50);
        if(lane.getRoute(index)!=null) {
            String label=new String();
            label=lane.getRoute(index).getLabel();
            g2.drawString(label,200,50);

            for (int i = 1; i < lane.getRoute(index).getLane().size() ; i++) {
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
                    else if (lane2.getRoute(index).getLane().get(i).getMaxVelocity() == 36)
                        g2.setColor(Color.GRAY);
                    else
                        g2.setColor(Color.darkGray);
                    g2.fillRect(j * 20, 160, 20, 40);
                }

            }
        }


    }


    Lane lane;
    Lane lane2;
    int index=0;



    class AnimationThread extends Thread {
        boolean sus = true;

        public void pause() {
            sus = true;
        }
        public void nextSection()
        {
            if(lane.size()-1<(index+1)) index=0;
else            index++;

        }
        public void previousSection()
        {
            if(index!=0)
                index--;
            else
                index=lane.size()-1;
            System.out.println(index);

        }
        public void addCar() throws Exception {
            if (lane.getRoute(index) != null)
                lane.getRoute(index).addCar(new Car());
            if (lane2.getRoute(index) != null)

                lane2.getRoute(index).addCar(new Car());

        }

        public synchronized void stoppasue() {
            sus = false;
            notify();
        }

        public void run() {
            while (true) {
                try {
                    lane2.simulate();
                    lane.simulate();
                    System.out.println(index);
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
                    Thread.sleep(1000);
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

    void onNext()
    {
        System.out.println("Next section");
        anim.nextSection();
    }

    public void onPrevious() {
        System.out.println("Previous Section");

        anim.previousSection();
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

       int distance[]={27,14,37,11,34,20, 21,12,11,15,3, 8, 7,11,23, 68,39,17,22,38, 0,0,0,0,0,0,0,0,0,0,0,0,0,0};
      int maxvelocity[]={36,54,54,54,54,54,54,54,54,54,54,54,54,54,54,36,36,36,36,36,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int distance2[]={27,14,37,11,34,20, 21,12,11,15,3, 8, 7,11,23, 68,39,17,22,38, 16,  17,16,21,18,13,29,47,10,37,4,15,5,30};
        int maxvelocity2[]={36,54,54,54,54,54,54,54,54,54,54,54,54,54,54,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36,36};

String[] labels={"Westerplatte","Gertrudy","Idziego","Straszewskiego","Podwale","Dunajewskiego","Basztowa"};




        this.lane=new Lane(distance,maxvelocity,"CW",true);
        this.lane2=new Lane(distance2,maxvelocity2,"NCW",true);
        for(int i=0;i<4;i++)
lane.getRoute(i).setLabel("Westerplatte");

        this.height=height;
        this.width=width;
        anim.start();
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
    }




}