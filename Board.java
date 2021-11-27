import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.BorderLayout;

public class Board extends JFrame {
    
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 800;
    
    public Board() {
        this.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        Panel p = new Panel();
        this.getContentPane().add(p,BorderLayout.EAST);
        this.getContentPane().add(new Menu(p),BorderLayout.WEST);
        this.setTitle("Game Of Life");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);   
    }
}
