import game.Cell;
import game.GameBoard;
import game.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Zhenya on 26.05.2017.
 */
public class GameSpaceUI extends JPanel {
    public static final int CELL_SIZE = 15;
    private JLabel statusbar;
    GameBoard myBoard;

   // private Image[] img;
    //private JLabel label;

    public GameSpaceUI(GameBoard myBoard,GameBoard opponentBoard, JLabel statusbar) {
        //img = (new ImageIcon("D:\\BURN\\Учеба (МИЭМ)\\JavaLearn\\Project\\BattleShip\\src\\game\\2.jpg")).getImage();

        addMouseListener(new CustomListener());
        this.statusbar = statusbar;
        this.myBoard = myBoard;
        this.setLayout(null);
        JLabel label1 = new JLabel("Opponent GameBoard");
        label1.setBounds(10, 10, 150, 15);
        add(label1);

        OpponentGameBoard opponentGameBoard = new OpponentGameBoard();
        opponentGameBoard.setBounds(10, 25, GameController.BOARD_SIZE * CELL_SIZE, GameController.BOARD_SIZE * CELL_SIZE);
        add(opponentGameBoard);

        JLabel label2 = new JLabel("My GameBoard");
        label2.setBounds(10, GameController.BOARD_SIZE * CELL_SIZE + 25, 150, 15);
        add(label2);

        MyGameBoard myGameBoard = new MyGameBoard();
        myGameBoard.setBounds(10,  GameController.BOARD_SIZE * CELL_SIZE + 40, GameController.BOARD_SIZE * CELL_SIZE, GameController.BOARD_SIZE * CELL_SIZE);
        add(myGameBoard);
    }

    public class CustomListener extends MouseAdapter {
        public void mousePressed (MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            if (Main.shooting.killedShipsCount < GameController.MAX_SHIPS) {

                if ((x > 10) && ((x - 10) < GameController.BOARD_SIZE * GameSpaceUI.CELL_SIZE + 10) && (y > 25) && ((y - 25) < GameController.BOARD_SIZE * GameSpaceUI.CELL_SIZE )) {
                    int cCol = (x - 10) / GameSpaceUI.CELL_SIZE;
                    int cRow = (y - 25) / GameSpaceUI.CELL_SIZE;

                    Cell cell = Main.opponentGameBoard.getCell(cCol, cRow);

                    if (cell.occupied) {
                        cell.checked = true;
                        cell.killed = true;
                        cell.ship.shipCells.remove(cell);
                        if (cell.ship.shipCells.isEmpty()){

                        }
                    } else
                        cell.checked = true;
                    //Ответный выстрел по полю пользователя
                    Main.shooting.makeShoot(Main.myGameBoard);
                }
            }
            if (Main.shooting.killedShipsCount == GameController.MAX_SHIPS) {
                statusbar.setText("End");
            }
            repaint();
        }
    }

    /**
     * public GameSpaceUI() {
     * JLabel label = new JLabel("MyGameBoard");
     * //img = (new ImageIcon("D:\\BURN\\Учеба (МИЭМ)\\JavaLearn\\Project\\BattleShip\\src\\game\\2.jpg")).getImage();
     * this.setLayout(new BorderLayout());
     * add(label, BorderLayout.NORTH);
     * add(new JLabel("MyGame"), BorderLayout.SOUTH);
     * setSize(200,200);
     * <p/>
     * }
     */


}
