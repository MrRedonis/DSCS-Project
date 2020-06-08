import Map.*;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Random;

public class SimulatePanel extends JPanel {
    boolean cond=false;
    int index = 0;
    ParseOSM data = new ParseOSM();

    double lat = 50.0539;
    double lon = 19.9315;
    int size = 45000;
    int odstep = 100;

    public void paintComponent(Graphics g) {
        System.out.println("Repaint");
        super.paintComponent(g);
        Random rand = new Random();

        Graphics2D g2 = (Graphics2D) g;

        for (Map.Entry<Pair<Long, Long>, Lane> entry : data.getLaneList().getLanes().entrySet()) {
            g2.setColor(Color.darkGray);
            Point begin = new Point((int)((entry.getValue().getBegin().getLon()*size-lon*size)),(int)((entry.getValue().getBegin().getLat()*size-lat*size)));
            Point end = new Point((int)((entry.getValue().getEnd().getLon()*size-lon*size)),(int)((entry.getValue().getEnd().getLat()*size-lat*size)));
            g2.drawLine(begin.x+odstep,height-begin.y,end.x+odstep,height-end.y);
            double retA = retA(begin,end);
            double linLen = lineLength(entry.getValue())*size;
            double ss = linLen/entry.getValue().getCells().size();
            for (int i = 0; i < entry.getValue().getCells().size(); i++) {
                if (entry.getValue().getCells().get(i).getOccupied()){
                    g2.setColor(entry.getValue().getCells().get(i).getCar().color);
                    g2.fillOval((int)(odstep+begin.x-ss*i),(int)(height-(begin.y-retA*i*ss)),5,5);
                }
                else {
                    g2.setColor(Color.gray);
                    g2.fillOval((int)(odstep+begin.x-ss*i),(int)(height-(begin.y-retA*i*ss)),5,5);
                }
            }
        }
    }

    private double retA(Point x, Point y){
        return ((double)y.y - x.y) /((double)y.x - x.x);
    }

    private double lineLength(Lane lane){
        return lane.getBegin().getLon()-lane.getEnd().getLon();
    }

    class AnimationThread extends Thread {
        boolean sus = true;

        public void pause() {
            sus = true;
        }

        public void addCar() throws Exception {
            data.addCars(50);
        }

        public synchronized void stoppasue() {
            sus = false;
            notify();
        }

        public void run() {
            while (true) {
                try {
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
                    Thread.sleep(100);
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
            this.height = height;
            this.width = width;
            anim.start();
            setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
        }
    }

