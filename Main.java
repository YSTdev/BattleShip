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
     * ������������� ������������ ����������
     * �������� ������ ����, ������ ��������� � �������� ������������
     */
    private void initUI() {

        statusbar = new JLabel("Hello!");

        //������ ����
        JMenuBar menuBar = new JMenuBar();
        //�������� ������ ����
        menuBar.add(gameMenu());
        menuBar.add(optionsGameMenu());
        //JMenu aboutMenu = new JMenu("About");
        menuBar.add(aboutMenu());
        //about.addSeparator();
        //���������� ��������� �� ������� �����
        setJMenuBar(menuBar);
        this.setLayout(new BorderLayout());
        gameSpaceUI = new GameSpaceUI(statusbar, mode);
        add(gameSpaceUI, BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);
        //��������� ��������� ������
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
     * ������� ���� "Create new game"
     *
     * @return
     */
    //TODO:�������� ������
    private JMenu gameMenu() {
        // �������� ����������� ���� � ������ "Start new game"
        JMenu game = new JMenu("Game");
        JMenuItem newgame = new JMenuItem("Start new game", new ImageIcon(""));
        JMenuItem endgame = new JMenuItem("Exit game");                            // ����� ���� "����� �� ���������"
        /**    // ���������� � ������ ���� �����������
         //  exit.setIcon(new ImageIcon("images/exit.png"));*/
        // ���������� ������ ���� "Start new game" � "Exit game"
        game.add(newgame);
        game.add(endgame);
        // ���������� �����������
        //  game.addSeparator();
        //  file.add(exit);

        newgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                GameController gameController = new GameController();
                //������ "In game"
                statusbar.setText("In Game");

                inGame = true;

                //InfoBoard.setLabels();
                gameSpaceUI.infoBoard.setLabels();

                gameController.startGame(level, mode);
                gameSpaceUI.repaint();
            }
        });

        //�������� ���� ��� ������� �� ����� ���� "Exit game"
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
                //TODO: ����� ����� ��� �������� ������� ����
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
