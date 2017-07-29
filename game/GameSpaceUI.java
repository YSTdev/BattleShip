//import game.GameBoard;
//import game.GameController;
//import game.EndGameListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Zhenya on 26.05.2017.
 */
public class GameSpaceUI extends JPanel implements ActionListener {
    public static final int CELL_SIZE = 15;
    private JLabel statusbar;
    private OpponentGameBoard opponentGameBoard;
    private MyGameBoard myGameBoard;
    public InfoBoard infoBoard;

    Timer timer = new Timer(500, this);

    public void endGame(String message) {
        this.repaint();
        statusbar.setText("End");
        infoBoard.setLabels();
        //endGame("");
        System.out.println(GameController.status);
        System.out.println(message);

        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(GameSpaceUI.this,
                    message,
                    "End of game!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * public void clientEndGame(){
     * statusbar.setText("End");
     * //endGame("");
     * JOptionPane.showMessageDialog(GameSpaceUI.this,
     * GameController.winner,
     * "End of game!",
     * JOptionPane.WARNING_MESSAGE);
     * }
     */
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == timer) {
            if (GameController.inGame)
                repaint();// this will call at every 1 second

            /**    if (GameController.inGame){
             checkEndGame();
             }
             */
        }
    }


    //private String mode;
    //private String status;

    // private Image[] img;
    //private JLabel label;

    public GameSpaceUI(JLabel statusbar) {
        //img = (new ImageIcon("D:\\BURN\\Учеба (МИЭМ)\\JavaLearn\\Project\\BattleShip\\src\\game\\2.jpg")).getImage();

        timer.start();
        //this.mode = mode;
        this.statusbar = statusbar;
        //this.status = status;
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
            System.out.println(GameController.mode);
            int x = e.getX();
            int y = e.getY();

            if (GameController.inGame) {

                if ((x > opponentGameBoard.getX()) && (x < opponentGameBoard.getX() + opponentGameBoard.getWidth()) && (y > opponentGameBoard.getY()) && (y < opponentGameBoard.getY() + opponentGameBoard.getWidth())) {
                    int cCol = (x - opponentGameBoard.getX()) / GameSpaceUI.CELL_SIZE;
                    int cRow = (y - opponentGameBoard.getY()) / GameSpaceUI.CELL_SIZE;

                    if (e.isMetaDown()) {
                        //System.out.print("Right click!");
                        if ((GameController.mode == "computer")) {
                            GameController.shooting.markCell(cCol, cRow);
                        }
                    } else {
                        //Выстрел игрока
                        /** if ((GameController.mode == "computer")) {
                         boolean successfulShot = GameController.shooting.makeUserShot(cCol, cRow);
                         GameController.opBoardData = GameBoard.changeBoardData(GameController.secondPlayerBoard);
                         //Ответный выстрел по полю пользователя
                         if (successfulShot) {
                         GameController.shooting.makeShoot(GameController.firstPlayerBoard);
                         GameController.myBoardData = GameBoard.makeMyBoardData(GameController.firstPlayerBoard);
                         statusbar.setText("Number of shots: " + GameController.shooting.shotCount);
                         }
                         }*/
                        if ((GameController.mode == "viaNet")) {
                            if (GameController.status == "server") {
                                if (GameController.queue == "first") {
                                    boolean successfulShot = GameController.shooting.makeUserShot(cCol, cRow, GameController.queue);

                                    if (successfulShot) {
                                        GameController.myBoardData = GameBoard.makeMyBoardData(GameController.firstPlayerBoard);
                                        GameController.opBoardData = GameBoard.changeBoardData(GameController.secondPlayerBoard);
                                        GameController.gameServer.sendData('M' + GameBoard.makeMyBoardData(GameController.secondPlayerBoard));
                                        GameController.gameServer.sendData('O' + GameBoard.changeBoardData(GameController.firstPlayerBoard));

                                        GameController.queue = "second";
                                        statusbar.setText("Number of shots: " + GameController.shooting.shotCount);
                                    }
                                }

                                //TODO: отправить данные о полях

                                System.out.println("Sending data");
                                // if (successfulShot){
                                //TODO: получить координаты выстрела
                                //TODO: обработать результаты выстрела и вернуть клиенту новые данные о полях
                                //  }
                            }

                            if (GameController.status == "client") {
                                //GameController.gameClient.startReading();
                                //GameController.gameClient.sendData("\n New Data ...");
                                GameController.gameClient.sendData("S" + cCol + cRow);

                                //TODO: получить строки с информацией о своем и чужом полях
                                //TODO: отправить координаты выстрела клиента и обработать
                                //TODO: получить новые данные о полях
                            }

                        }
                    }

                    opponentGameBoard.repaint();
                    myGameBoard.repaint();

                    infoBoard.changeLabels();
                    //InfoBoard.changeLabels();
                }
                /** if ((GameController.winner != null)) {
                 //if ((GameController.shooting.killedShipsCount == GameController.MAX_SHIPS) || (GameController.shooting.killedShipsCountUser == GameController.MAX_SHIPS)) {
                 statusbar.setText("End");

                 //String winner = GameController.endGame();
                 //endGame("");
                 JOptionPane.showMessageDialog(GameSpaceUI.this,
                 GameController.winner,
                 "End of game!",
                 JOptionPane.WARNING_MESSAGE);
                 }*/

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
