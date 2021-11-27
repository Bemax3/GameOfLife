import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {

    public static final int SCREEN_WIDTH = 200;
    public static final int SCREEN_HEIGHT = 800;

    public Menu(Panel p) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        JButton b1 = new JButton("Start !");
        JButton b2 = new JButton("Stop !");
        JButton b3 = new JButton("Reset !");
        JButton b4 = new JButton("Slow Down !");
        JButton b5 = new JButton("Speed Up !");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                p.setRunning(true);
                p.repaint();
            }
        });
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                p.setRunning(false);
                p.repaint();
            }
        });
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                p.resetCells();
                p.repaint();
            }
        });
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                p.slowDown();
                p.repaint();
            }
        });
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                p.speedUp();
                p.repaint();
            }
        });
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        this.add(b5);
    }    
}
