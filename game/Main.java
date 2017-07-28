
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

/**
 * Created by Zhenya on 22.04.2017.
 */
public class Main extends JFrame {

    final private int MAX_HEIGHT = 450;
    final private int MAX_WIDTH = 300;
    //private String level = "simple";
    //private String mode = "computer";
    private JLabel statusbar;
    public static GameSpaceUI gameSpaceUI;
    private NetModeFrame netModeFrame;
    //public static boolean inGame = false;
    public GameController gameController;

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
        gameSpaceUI = new GameSpaceUI(statusbar);
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

                gameController = new GameController(gameSpaceUI);
                //������ "In game"
                statusbar.setText("In Game");

                gameController.inGame = true;

                //InfoBoard.setLabels();
                gameSpaceUI.infoBoard.setLabels();


                //mode = NetModeFrame.mode;
                gameController.startGame(GameController.level, GameController.mode, GameController.status);
                gameSpaceUI.repaint();

                //gameController.addListener(gameSpaceUI);
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

        JMenu gameMode = new JMenu("Mode");
        JRadioButtonMenuItem computer = new JRadioButtonMenuItem("Single player");
        JRadioButtonMenuItem viaNet = new JRadioButtonMenuItem("Two players");
        computer.setSelected(true);

        ButtonGroup modegroup = new ButtonGroup();
        modegroup.add(computer);
        modegroup.add(viaNet);
        gameMode.add(computer);
        gameMode.add(viaNet);

        options.add(gameMode);


        simple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                GameController.level = "simple";
                System.out.println("simple mode");
            }
        });

        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                GameController.level = "hard";
                System.out.println("hard mode");
            }

        });

        computer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("computer mode");

            }
        });

        viaNet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //TODO: ����� ����� ��� �������� ������� ����
                netModeFrame = new NetModeFrame();
                netModeFrame.setVisible(true);
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
