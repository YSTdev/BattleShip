import game.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Zhenya on 22.04.2017.
 */
public class Main extends JFrame {

    final private int MAX_HEIGHT = 450;
    final private int MAX_WIDTH = 300;
    private String level = "simple";
    private String mode = "computer";
    private JLabel statusbar;
    private GameSpaceUI gameSpaceUI;
    public static boolean inGame = false;

    //static public Shooting shooting;


    public Main() {

        initUI();
    }

    public void setStatus(String status) {
        this.statusbar.setText(status);
    }

    /**
     * Инициализация графического интерфейса
     * Создание строки меню, строки состояния и игрового пространства
     */
    private void initUI() {

        statusbar = new JLabel("Hello!");

        //Строка меню
        JMenuBar menuBar = new JMenuBar();
        //Элементы строки меню
        menuBar.add(gameMenu());
        menuBar.add(optionsGameMenu());
        //JMenu aboutMenu = new JMenu("About");
        menuBar.add(aboutMenu());
        //about.addSeparator();
        //добавление элементов на главный фрейм
        setJMenuBar(menuBar);
        this.setLayout(new BorderLayout());
        gameSpaceUI = new GameSpaceUI(statusbar, mode);
        add(gameSpaceUI, BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);
        //настройка основного фрейма
        setTitle("BattleShip");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(MAX_WIDTH, MAX_HEIGHT);
        /**pack();*/
    }

    private JMenuItem aboutMenu() {

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg) {
                System.out.print("About click!");

                JOptionPane.showMessageDialog(Main.this,
                        "Bla-bla",
                        "About this game",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        return about;
    }

    /**
     * Элемент меню "Create new game"
     *
     * @return
     */
    //TODO:добавить иконки
    private JMenu gameMenu() {
        // Создание выпадающего меню и пункта "Start new game"
        JMenu game = new JMenu("Game");
        JMenuItem newgame = new JMenuItem("Start new game", new ImageIcon(""));
        JMenuItem endgame = new JMenuItem("Exit game");                            // Пункт меню "выход из программы"
        /**    // Добавление к пункту меню изображения
         //  exit.setIcon(new ImageIcon("images/exit.png"));*/
        // Добавление пунков меню "Start new game" и "Exit game"
        game.add(newgame);
        game.add(endgame);
        // Добавление разделителя
        //  game.addSeparator();
        //  file.add(exit);

        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                GameController gameController = new GameController();
                //Статус "In game"
                statusbar.setText("In Game");

                inGame = true;

                //InfoBoard.setLabels();
                gameSpaceUI.infoBoard.setLabels();

                gameController.startGame(level, mode);
                gameSpaceUI.repaint();
            }
        });

        //Закрытие окна при нажатии на пункт меню "Exit game"
        endgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        return game;
    }


    private JMenu optionsGameMenu() {

        JMenu options = new JMenu("Options");

        JMenu hardness = new JMenu("Hardness");
        JRadioButtonMenuItem simple = new JRadioButtonMenuItem("Simple");
        JRadioButtonMenuItem hard = new JRadioButtonMenuItem("Hard");
        simple.setSelected(true);

        ButtonGroup levelgroup = new ButtonGroup();
        levelgroup.add(simple);
        levelgroup.add(hard);
        hardness.add(simple);
        hardness.add(hard);

        options.add(hardness);

        //options.addSeparator();

        JMenu mode = new JMenu("Mode");
        JRadioButtonMenuItem computer = new JRadioButtonMenuItem("Single player");
        JRadioButtonMenuItem viaNet = new JRadioButtonMenuItem("Two players");
        computer.setSelected(true);

        ButtonGroup modegroup = new ButtonGroup();
        modegroup.add(computer);
        modegroup.add(viaNet);
        mode.add(computer);
        mode.add(viaNet);

        options.add(mode);



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

        viaNet.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //TODO: новая форма для создания сетевой игры
            }
        });

        return options;

    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main ex = new Main();
                ex.setVisible(true);
            }
        });
    }
}
