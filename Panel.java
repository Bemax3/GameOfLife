import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;


public class Panel extends JPanel{
    
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static final int UNIT_SIZE = 25;
    public static final int dim = SCREEN_WIDTH / UNIT_SIZE;
    public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    public boolean running = false;
    public int sleepTime = 100;
    public static int[][] cells = new int[dim][dim];
    public static int[][] cellsTMP = new int[dim][dim];
    
    
    public Panel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double x = e.getX();
                double y = e.getY();                
                toggleCell(x, y);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }
        });

        this.init();
    }
    
    public void toggleCell(double x, double y) {
        int i = (int) Math.floor(x / UNIT_SIZE);
        int j = (int) Math.floor(y / UNIT_SIZE);
        if (cells[i][j] == 1)
            cells[i][j] = 0;
        else
            cells[i][j] = 1;
        this.repaint();
    }
    
    public void speedUp() {
        this.sleepTime -= 20;
        if (this.sleepTime < 0)
            this.sleepTime = 0;
    }
    public void slowDown() {
        this.sleepTime += 20;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    public void init() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                cells[i][j] = 0;
            }
        }
    }
            
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            this.draw(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) throws InterruptedException {
        paintCells(g);
        if (running) {
            nextGeneration();
            paintCells(g);
            TimeUnit.MILLISECONDS.sleep(this.sleepTime);
            repaint();
        }
    }
    
    public static void paintCells(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < SCREEN_HEIGHT; i += UNIT_SIZE) {
            g.drawLine(0, i, SCREEN_WIDTH, i);
            g.drawLine(i, 0, i, SCREEN_HEIGHT);
        }
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (cells[i][j] == 1) {
                    g.setColor(Color.GREEN);
                    g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE - 1, UNIT_SIZE - 1);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE - 1, UNIT_SIZE - 1);
                }
            }
        }
    }

    public static int neighborsCount(int i, int j) {
        int count = 0;
        int tmpk;
        int tmpl;
        if (cells[i][j] == 1)
            count--;
        for (int k = i - 1; k <= i + 1; k++) {
            if (k < 0)
                tmpk = 31;
            else if (k > 31)
                tmpk = 0;
            else
                tmpk = k;
            for (int l = j - 1; l <= j + 1; l++) {
                if (l < 0)
                    tmpl = 31;
                else if (l > 31)
                    tmpl = 0;
                else
                    tmpl = l;
                if (cells[tmpk][tmpl] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public static void nextGeneration() {
        int[][] newBoardState = new int[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                /* Handle the case where the cell is alive. */
                if (cells[i][j] == 1) {
                    if (neighborsCount(i, j) <= 1) {
                        newBoardState[i][j] = 0;
                    } else if (neighborsCount(i, j) > 3) {
                        newBoardState[i][j] = 0;
                    } else {
                        newBoardState[i][j] = 1;
                    }
                }
                /* Handle the case where the cell is dead. */
                else {
                    if (neighborsCount(i, j) == 3) {
                        newBoardState[i][j] = 1;
                    } else {
                        newBoardState[i][j] = 0;
                    }
                }
            }
        }
        cells = newBoardState;
    }

    public void resetCells() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                cells[i][j] = 0;
            }
        }
    }

    // private static int countNeighbors(int i, int j) {
    //     int res = 0;
    //     if (i == 0 && j == 0) {2d
    //         res += (cells[0][1] + cells[1][0] + 
    //                 cells[1][1]);
    //     }
    //     else if (i == 0 && j == dim - 1) {
    //         res += (cells[0][j-1] + cells[1][j] + 
    //                 cells[1][j-1]);
    //     }
    //     else if (i == dim - 1 && j == 0) {
    //         res += (cells[i-1][0] + cells[i][1] + 
    //                 cells[i-1][1]);
    //     }
    //     else if (i == dim - 1 && j == dim - 1) {
    //         res += (cells[i-1][j] + cells[i][j-1] + 
    //                 cells[i-1][j-1]);
    //     }
    //     else if (i == 0) {
    //         res += (cells[0][j-1] + cells[1][j-1] + 
    //                 cells[1][j] + cells[1][j+1] + 
    //                 cells[0][j+1]);
    //     }
    //     else if (i == dim - 1) {
    //         res += (cells[i][j-1] + cells[i-1][j-1] + 
    //                 cells[i-1][j] + cells[i-1][j+1] + 
    //                 cells[i][j+1]);
    //     }
    //     else if (j == 0 ) {
    //         res += (cells[i-1][0] + cells[i-1][1] + 
    //                 cells[i][1] + cells[i+1][1] + 
    //                 cells[i+1][0]);
    //     }
    //     else if (j == dim - 1) {
    //         res += (cells[i-1][j] + cells[i-1][j-1] + 
    //                 cells[i][j-1] + cells[i+1][j-1] + 
    //                 cells[i+1][j]);
    //     }
    //     else {
    //         res += (cells[i-1][j-1] + cells[i-1][j] + 
    //                 cells[i-1][j+1] + cells[i][j-1] + 
    //                 cells[i][j+1] + cells[i+1][j-1] + 
    //                 cells[i+1][j] + cells[i+1][j+1]);
    //     }

    //     return res;
    // }
}