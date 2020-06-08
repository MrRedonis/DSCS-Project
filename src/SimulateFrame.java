import javax.swing.*;
import java.awt.*;

public class SimulateFrame extends JFrame {

    private SimulatePanel simpanel = new SimulatePanel(700, 630);

    SimulateFrame() throws Exception {
        super("Simulate");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel opt = new JPanel();

        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton plus = new JButton("Add 50 cars");

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

        SimulateFrame frame = new SimulateFrame();
        frame.setSize(900, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}