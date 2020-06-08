import Map.Lane;
import Map.ParseOSM;
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
            Point begin = new Point((int)((entry.getValue().getBegin().getLat()-lat)*size),(int)((entry.getValue().getBegin().getLon()-lon)*size));
            Point end = new Point((int)((entry.getValue().getEnd().getLat()-lat)*size),(int)((entry.getValue().getEnd().getLon()-lon)*size));
            g2.drawLine(begin.x+odstep,begin.y+odstep,end.x+odstep,end.y+odstep);
            double retA = retA(entry.getValue());
            double linLen = lineLength(entry.getValue());
            double linLen2 = lineLength2(entry.getValue());
            double ss = linLen/entry.getValue().getCells().size();
            double ss2 = linLen2/entry.getValue().getCells().size();
            for (int i = 0; i < entry.getValue().getCells().size() - 1; i++) {
                if (entry.getValue().getCells().get(i).getOccupied()){
                    g2.setColor(entry.getValue().getCells().get(i).getCar().color);
                    g2.fillOval((int)(odstep+begin.x+ss*i),(int)(odstep+begin.y+retA*i*ss2),7,7);
                }
                else {
                    g2.setColor(Color.gray);
                    g2.fillOval((int)(odstep+begin.x+ss*i),(int)(odstep+begin.y+retA*i*ss2),7,7);
                }
            }
        }
    }

    private double retA(Lane lane){
        Point x = new Point((int)((lane.getBegin().getLat()-lat)*size),(int)((lane.getBegin().getLon()-lon)*size));
        Point y = new Point((int)((lane.getEnd().getLat()-lat)*size),(int)((lane.getEnd().getLon()-lon)*size));

        double a = ((double)y.y - x.y) /((double)y.x - x.x);
        return a;
    }

    private double lineLength(Lane lane){
        return lane.getBegin().getLon()-lane.getEnd().getLon();
    }

    private double lineLength2(Lane lane){
        return lane.getBegin().getLat()-lane.getEnd().getLat();
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

