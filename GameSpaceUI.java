import game.GameController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Zhenya on 26.05.2017.
 */
public class GameSpaceUI extends JPanel {
    public static final int CELL_SIZE = 15;
    private JLabel statusbar;
    private OpponentGameBoard opponentGameBoard;
    private MyGameBoard myGameBoard;
    public InfoBoard infoBoard;
    private String mode;

    // private Image[] img;
    //private JLabel label;

    public GameSpaceUI(JLabel statusbar, String mode) {
        //img = (new ImageIcon("D:\\BURN\\Учеба (МИЭМ)\\JavaLearn\\Project\\BattleShip\\src\\game\\2.jpg")).getImage();

        this.mode = mode;
        this.statusbar = statusbar;
        this.setLayout(null);

        addMouseListener(new ShotListener());

        JLabel label1 = new JLabel("Opponent GameBoard");
        label1.setBounds(10, 10, 150, 15);
        add(label1);

        opponentGameBoard = new OpponentGameBoard();
        opponentGameBoard.setBounds(10, label1.getY() + label1.getHeight(), GameController.BOARD_SIZE * CELL_SIZE, GameController.BOARD_SIZE * CELL_SIZE);
        add(opponentGameBoard);

        JLabel label2 = new JLabel("My GameBoard");
        label2.setBounds(10, opponentGameBoard.getY() + opponentGameBoard.getHeight() + 5, 150, 15);
        add(label2);

        myGameBoard = new MyGameBoard();
        myGameBoard.setBounds(10, label2.getY() + label2.getHeight(), GameController.BOARD_SIZE * CELL_SIZE, GameController.BOARD_SIZE * CELL_SIZE);
        add(myGameBoard);

        infoBoard = new InfoBoard();
        infoBoard.setBounds(opponentGameBoard.getX() + opponentGameBoard.getWidth() + 20, opponentGameBoard.getY(), 500, 500);
        add(infoBoard);
    }

    public class ShotListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            if (Main.inGame) {

                if ((x > opponentGameBoard.getX()) && (x < opponentGameBoard.getX() + opponentGameBoard.getWidth()) && (y > opponentGameBoard.getY()) && (y < opponentGameBoard.getY() + opponentGameBoard.getWidth())) {
                    int cCol = (x - opponentGameBoard.getX()) / GameSpaceUI.CELL_SIZE;
                    int cRow = (y - opponentGameBoard.getY()) / GameSpaceUI.CELL_SIZE;

                    if (e.isMetaDown()) {
                        System.out.print("Right click!");
                        GameController.shooting.markCell(cCol, cRow);
                    } else {
                        //Выстрел игрока
                        boolean successfulShot = GameController.shooting.makeUserShot(cCol, cRow);
                        //Ответный выстрел по полю пользователя
                        if (successfulShot && (mode == "computer")) {
                            GameController.shooting.makeShoot(GameController.firstPlayerBoard);
                            statusbar.setText("Number of shots: " + GameController.shooting.shotCount);
                        }
                    }
                }
                opponentGameBoard.repaint();
                myGameBoard.repaint();

                infoBoard.changeLabels();
                //InfoBoard.changeLabels();

                if (GameController.endGame() != null){
                //if ((GameController.shooting.killedShipsCount == GameController.MAX_SHIPS) || (GameController.shooting.killedShipsCountUser == GameController.MAX_SHIPS)) {
                    statusbar.setText("End");

                    String winner = GameController.endGame();
                    //endGame("");
                    JOptionPane.showMessageDialog(GameSpaceUI.this,
                            winner,
                            "End of game!",
                            JOptionPane.WARNING_MESSAGE);
                }

            }

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
