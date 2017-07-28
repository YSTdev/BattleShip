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

    final private int MAX_HEIGHT = 100;
    final private int MAX_WIDTH = 200;
    //public static String status="server";
    //public static String mode = "computer";
    private String address = "";
    JTextField addressfield;

    public NetModeFrame() {
        init();
    }

    private void init() {

        addressfield = new JTextField();
        add(addressfield, BorderLayout.NORTH);
        add(serverButton(), BorderLayout.EAST);
        add(clientButton(), BorderLayout.WEST);
        setTitle("Two players mode");
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(MAX_WIDTH, MAX_HEIGHT);
    }

    private JButton serverButton() {
        JButton serverButton = new JButton("server");
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.status = "server";
                GameController.mode = "viaNet";
                System.out.println("Server button clicked!");

                System.out.println("starting server");
                GameController.gameServer = new GameServer();
                GameController.gameServer.go();
            }
        });

        return serverButton;
    }

    private JButton clientButton() {
        JButton clientButton = new JButton("client");
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                address = addressfield.getText();
                GameController.status = "client";
                GameController.mode = "viaNet";
                System.out.println("Client button clicked!");

                System.out.println("starting client");
                GameController.gameClient = new GameClient();
                GameController.gameClient.setUpNetworking(address);
                //   gameClient.go();

                System.out.println("\n continue ...");
            }
        });
        return clientButton;
    }

}
