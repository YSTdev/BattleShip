//import game.GameClient;
//import game.GameController;
//import game.GameServer;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

/**
 * Created by Zhenya on 25.07.2017.
 */
public class NetModeFrame extends JFrame {

    final private int MAX_HEIGHT = 210;
    final private int MAX_WIDTH = 225;
    //public static String status="server";
    //public static String mode = "computer";
    private String address = "";
    JTextField addressfield;
    JLabel serverLabel;
    JRadioButton server;
    JRadioButton client;

    public NetModeFrame() {
        init();
    }

    private void init() {
/**
 JPanel buttonGroup = new JPanel();
 buttonGroup.setLayout(null);
 buttonGroup.setBounds(0,100,100,500);
 buttonGroup.setVisible(true);

 buttonGroup.add(serverButton());
 buttonGroup.add(clientButton());
 */

        this.setLayout(null);

        //add(buttonPanel(), BorderLayout.NORTH);
        add(choicePanel());

        setTitle("Two players mode");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(MAX_WIDTH, MAX_HEIGHT);

    }

    /**
     * private JPanel buttonPanel(){
     * <p/>
     * JPanel buttonPanel = new JPanel();
     * //setLayout(new FlowLayout());
     * buttonPanel.setLayout(null);
     * buttonPanel.setBounds(10, 400, 300, 100);
     * //setBackground(Color.cyan);
     * //setVisible(true);
     * JButton button = new JButton("example");
     * button.setBounds(20,130,50,30);
     * add(button);
     * //setSize(500, 200);
     * //setSize(500, 300);
     * //pack();
     * return buttonPanel;
     * }
     */
    private JPanel choicePanel() {
        JPanel choicePanel = new JPanel();
        this.setBounds(10, 50, 240, 200);
        setLayout(null);
        setBackground(Color.red);

        server = new JRadioButton("Server mode");
        //server.setEnabled(true);
        server.setSelected(true);
        server.setBounds(10, 10, 100, 30);

        client = new JRadioButton("Client mode");
        client.setBounds(10, 50, 100, 30);
        client.setForeground(Color.gray);
        client.addItemListener(new ShowListener());

        ButtonGroup modegroup = new ButtonGroup();
        modegroup.add(server);
        modegroup.add(client);

        add(server);
        add(client);

        serverLabel = new JLabel("Server address");
        serverLabel.setForeground(Color.gray);
        serverLabel.setBounds(10, 90, 100, 20);
        add(serverLabel);

        addressfield = new JTextField();
        addressfield.setEnabled(false);
        addressfield.setBounds(serverLabel.getWidth() + 10, 90, 100, 20);
        add(addressfield);

        JButton okButton = new JButton("Ok");
        okButton.setBounds(10, 130, 75, 25);
        add(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                GameController.mode = "viaNet";

                if (GameController.status == "server") {
                    System.out.println("starting server");
                    GameController.gameServer = new GameServer();
                    GameController.gameServer.go();
                }
                if (GameController.status == "client") {
                    if (addressfield.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(Main.netModeFrame, "You need to enter server ip!");
                    } else {
                        System.out.println("starting client");
                        GameController.gameClient = new GameClient();
                        GameController.gameClient.setUpNetworking(addressfield.getText());
                    }
                }
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(135, 130, 75, 25);
        add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            //        public class CancelListener implements ActionListener{
            public void actionPerformed(ActionEvent event) {
                Main.computer.setSelected(true);
                dispose();
            }
        });

        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                GameController.status = "server";
                System.out.println(GameController.status);
            }
        });

        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                GameController.status = "client";
                System.out.println(GameController.status);
            }
        });

        //add(buttonPanel());
        return choicePanel;
    }

    /**
     * private JButton serverButton() {
     * JButton serverButton = new JButton("server");
     * serverButton.setBounds(10, 10, 40, 10);
     * serverButton.addActionListener(new ActionListener() {
     *
     * @Override public void actionPerformed(ActionEvent e) {
     * GameController.status = "server";
     * GameController.mode = "viaNet";
     * System.out.println("Server button clicked!");
     * <p/>
     * System.out.println("starting server");
     * GameController.gameServer = new GameServer();
     * GameController.gameServer.go();
     * }
     * });
     * <p/>
     * return serverButton;
     * }
     * <p/>
     * private JButton clientButton() {
     * JButton clientButton = new JButton("client");
     * clientButton.addActionListener(new ActionListener() {
     * @Override public void actionPerformed(ActionEvent e) {
     * address = addressfield.getText();
     * GameController.status = "client";
     * GameController.mode = "viaNet";
     * System.out.println("Client button clicked!");
     * <p/>
     * System.out.println("starting client");
     * GameController.gameClient = new GameClient();
     * GameController.gameClient.setUpNetworking(address);
     * //   gameClient.go();
     * <p/>
     * System.out.println("\n continue ...");
     * }
     * });
     * return clientButton;
     * }
     */

    public class ShowListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {

            Color color = UIManager.getColor("serverLabel.background");
            if (e.getStateChange() == ItemEvent.SELECTED) {
                addressfield.setEnabled(true);
                serverLabel.setForeground(color);
                client.setForeground(color);
                server.setForeground(Color.gray);

            } else {
                addressfield.setEnabled(false);
                serverLabel.setForeground(Color.gray);
                client.setForeground(Color.gray);
                server.setForeground(color);
            }
        }
    }

}
