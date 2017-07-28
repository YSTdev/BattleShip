//import game.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Zhenya on 20.07.2017.
 */
public class InfoBoard extends JPanel {
    private Image img;
    private JLabel[] labels;

    public InfoBoard() {

        this.setLayout(null);
        img = new ImageIcon("4my.jpg").getImage();
        labels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds(0, (i * GameSpaceUI.CELL_SIZE + i * 10), 25, 15);
            this.add(labels[i]);
        }
        setLabels();
    }

    public  void setLabels() {

        for (int i = 0; i < 4; i++) {
            labels[i].setText((i + 1) + " x ");
        }
    }

    public  void changeLabels(){

        for (int i = 0; i < 4; i++) {
            labels[i].setText(GameController.shooting.shipsAlive[3 - i] + " x ");
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        for (int y = 0; y < 4; y++) {
            i++;
            for (int x = 0; x < 5 - i; x++) {
                g.drawImage(img, x * GameSpaceUI.CELL_SIZE + labels[y].getWidth(), (y * GameSpaceUI.CELL_SIZE + y * 10), null);
            }
        }

    }
}
