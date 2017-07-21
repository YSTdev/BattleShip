import game.Cell;
import game.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Zhenya on 31.05.2017.
 */
public class OpponentGameBoard extends JPanel {


    private final int IMG_NUM = 5;
    private final int EMPTY_CELL = 0;
    private final int CHECKED_CELL = 1;
    private final int KILLED_CELL = 2;
    private final int KILLED_SHIP_CELL = 3;
    private final int MARKED_CELL = 4;

    private Image[] img;

    public OpponentGameBoard() {

        img = new Image[IMG_NUM];

        for (int i = 0; i < IMG_NUM; i++) {
            img[i] = (new ImageIcon(i + "op.jpg")).getImage();
        }

        //  this.setLayout(null);
        //  add(new GameSpaceUI(), BorderLayout.CENTER);
        //  add(statusbar, BorderLayout.SOUTH);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (Main.inGame) {
            for (int x = 0; x < GameController.BOARD_SIZE; x++)
                for (int y = 0; y < GameController.BOARD_SIZE; y++) {
                    Cell cell = GameController.opponentGameBoard.getCell(x, y);

                    if (cell.checked){
                        if (cell.killed){
                            if (!cell.ship.shipCells.isEmpty()){
                                    g.drawImage(img[KILLED_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                            }
                            else
                                g.drawImage(img[KILLED_SHIP_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                        }
                        else
                            g.drawImage(img[CHECKED_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                    }
                    else
                        g.drawImage(img[EMPTY_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);

                    if (cell.marked)
                        g.drawImage(img[MARKED_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);


                    /**
                    if (cell.killed)
                        g.drawImage(img[KILLED_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                    else if (cell.checked)
                        g.drawImage(img[CHECKED_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                    else
                        g.drawImage(img[EMPTY_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                    /**
                    if (cell.ship!=null){
                        if (!cell.ship.shipCells.isEmpty()) {
                            if (cell.killed)
                                g.drawImage(img[KILLED_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                            else if (cell.occupied)
                                g.drawImage(img[SHIP_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                        }
                        else
                            g.drawImage(img[KILLED_SHIP_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                    }
                    else {
                        if (cell.checked)
                            g.drawImage(img[CHECKED_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                        else
                            g.drawImage(img[EMPTY_CELL], x * GameSpaceUI.CELL_SIZE, y * GameSpaceUI.CELL_SIZE, null);
                    }
                     */
                }

        } else {
            for (int i = 0; i < GameController.BOARD_SIZE; i++)
                for (int j = 0; j < GameController.BOARD_SIZE; j++) {
                    g.drawImage(img[EMPTY_CELL], i * GameSpaceUI.CELL_SIZE, j * GameSpaceUI.CELL_SIZE, null);
                }
        }
    }



}
