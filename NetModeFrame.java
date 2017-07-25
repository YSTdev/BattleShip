import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Zhenya on 25.07.2017.
 */
public class NetModeFrame extends JFrame {

    final private int MAX_HEIGHT = 100;
    final private int MAX_WIDTH = 200;
    private String status="server";
    private String address = "";
    JTextField addressfield;

    public NetModeFrame(){
        init();
    }

    public String getStatus(){
        return status;
    }

    private void init(){

        addressfield = new JTextField();
        add(addressfield, BorderLayout.NORTH);
        add(serverButton(), BorderLayout.EAST);
        add(clientButton(), BorderLayout.WEST);
        setTitle("Two players mode");
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(MAX_WIDTH, MAX_HEIGHT);
    }

    private JButton serverButton (){
        JButton serverButton = new JButton();
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = "server";
                System.out.println("Server button clicked!");
            }
        });

        return serverButton;
    }

    private JButton clientButton (){
        JButton clientButton = new JButton();
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                address = addressfield.getText();
                status = "client";
                System.out.println("Client button clicked!");
            }
        });
        return clientButton;
    }

}
