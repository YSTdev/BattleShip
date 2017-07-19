import game.Cell;
import game.GameBoard;
import game.GameController;
import game.Shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Zhenya on 22.04.2017.
 */
public class Main extends JFrame{

    final private int MAX_HEIGHT = 500;
    final private int MAX_WIDTH = 300;
    private String level = "simple";
    private JLabel statusbar;
    private GameSpaceUI gameSpaceUI;
    public static boolean inGame = false;
    static public GameBoard myGameBoard = new GameBoard(GameController.MAX_SHIPS, GameController.BOARD_SIZE);
    static public GameBoard opponentGameBoard = new GameBoard(GameController.MAX_SHIPS, GameController.BOARD_SIZE);
    static public Shooting shooting;


    public Main(){

        initUI();
    }

    public void setStatus(String status) {
        this.statusbar.setText(status);
    }

    private void initUI(){

        statusbar = new JLabel("Hello!");

     //   JPanel MyGameBoard = new JPanel();
     //   MyGameBoard.add(statusbar);
     //   add(statusbar, BorderLayout.SOUTH);
     //   add(new MyGameBoard(statusbar));
      //  MyGameBoard.setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createGameMenu());
        menuBar.add(optionsGameMenu());


        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        editMenu.addSeparator();
        setJMenuBar(menuBar);

        this.setLayout(new BorderLayout());
        gameSpaceUI = new GameSpaceUI(myGameBoard, opponentGameBoard, statusbar);
        add(gameSpaceUI,BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);



        setTitle("BattleShip");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(MAX_WIDTH, MAX_HEIGHT);
        //pack();
    }

    private JMenu createGameMenu()
    {
        JMenu game = new JMenu("Game");                                              // Создание выпадающего меню
        JMenuItem newgame = new JMenuItem("Start new game", new ImageIcon("")); // Пункт меню "Открыть" с изображением
    //    JMenuItem endgame = new JMenuItem(new ExitAction());                            // Пункт меню из команды с выходом из программы
        // Добавление к пункту меню изображения
      //  exit.setIcon(new ImageIcon("images/exit.png"));
        // Добавим в меню пункта open
        game.add(newgame);
        // Добавление разделителя
      //  game.addSeparator();
      //  file.add(exit);

        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("ActionListener.actionPerformed : open");
                GameController gameController = new GameController();

                statusbar.setText("In Game");
                for (int i=0; i<100; i++) {
                    myGameBoard = gameController.initGameBoard(myGameBoard);
                    opponentGameBoard = gameController.initGameBoard(opponentGameBoard);
                    if ((myGameBoard!=null)&&(opponentGameBoard!=null))
                        break;
                }

                shooting = new Shooting(myGameBoard);
                if (level == "simple") {
                    shooting.simpleShooting();
                }

                if (level == "hard") {
                    shooting.smartShooting(myGameBoard);
                }

            //    gameController.startGame(level, myGameBoard, shooting);
                inGame = true;
                repaint();
            }
        });

        return game;
    }


    private JMenu optionsGameMenu(){

        JMenu options = new JMenu("Options");
        JRadioButtonMenuItem simple = new JRadioButtonMenuItem("Simple");
        JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");
        simple.setSelected(true);

        ButtonGroup levelgroup = new ButtonGroup();
        levelgroup.add(simple);
        levelgroup.add(hard);
        options.add(simple);
        options.add(hard);

        simple.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                level = "simple";
            }
        });

        hard.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                level = "hard";
            }

        });

        return options;

    }



/**
    Action exitAction = new AbstractAction("Exit"); // Пункт меню "Exit".

        public void actionPerformed(ActionEvent event)
        {// Фрагмент программы, обрабатывающий событие.
            System.exit(0);
        }

*/
    public static void main(String[] args) {
    /**
        GameController gameController = new GameController();

        gameController.startGame();
*/
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main ex = new Main();
                ex.setVisible(true);
            }
        });
    }
}
