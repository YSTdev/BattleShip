import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Zhenya on 25.07.2017.
 */
public class GameClient {

    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    /**
     * private List<EndGameListener> listeners = new ArrayList<EndGameListener>();
     * <p/>
     * public void addListener(EndGameListener toAdd) {
     * listeners.add(toAdd);
     * }
     * public void endGame() {
     * System.out.println("End Game!!!");
     * // Notify everybody that may be interested.
     * for (EndGameListener hl : listeners)
     * hl.endGame();
     * }
     * public void clientEndGame() {
     * System.out.println("End Game!!!");
     * for (EndGameListener hl : listeners)
     * hl.endGame();
     * }
     */
    public void go() {
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    /**
     * public void startReading(){
     * (new Thread(){public void run(){
     * Thread readerThread = new Thread(new IncomingReader());
     * readerThread.start();
     * }}).start();
     * }
     */
    public boolean setUpNetworking(final String address) {
        (new Thread() {
            public void run() {
                try {
                    sock = new Socket(address, 5000);
                    InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamReader);
                    writer = new PrintWriter(sock.getOutputStream());
                    System.out.println("networking established");
                    JOptionPane.showMessageDialog(Main.netModeFrame, "Connected successfully!");

                    go();
                } catch (Exception ex) {
                    System.out.println("Cannot establish network connection");
                    JOptionPane.showMessageDialog(Main.netModeFrame, "Connection failed!");
                    ex.printStackTrace();
                }
            }
        }).start();
        return true;
    }

    public class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                System.out.println("Waiting for data");
                while ((message = reader.readLine()) != null) {
                    if (message.charAt(0) == 'S') {
                        int a = JOptionPane.showConfirmDialog(Main.netModeFrame, "New Game from server. Accept?");
                        if (a == JOptionPane.YES_OPTION) {
                            Main.start();
                        }
                        if (a == JOptionPane.CLOSED_OPTION) {
                            sendData("N");
                        }
                        System.out.println("Read opData: " + message);
                    }

                    if (message.charAt(0) == 'O') {
                        GameController.opBoardData = message.substring(1);
                        System.out.println("Read opData: " + message);
                    }

                    if (message.charAt(0) == 'G') {
                        if (GameController.inGame) {
                            for (int i = 0; i < 4; i++) {
                                GameController.shooting.opShipsAlive[i] = Character.getNumericValue(message.charAt(i + 1));
                                Main.gameSpaceUI.infoBoard.changeLabels();
                            }
                        }
                    }

                    if (message.charAt(0) == 'M') {
                        GameController.myBoardData = message.substring(1);
                        System.out.println("Read myData: " + message);
                    }

                    if (message.charAt(0) == 'E') {
                        GameController.winner = message.substring(1);
                        System.out.println("End game: " + GameController.winner);
                        Main.gameSpaceUI.infoBoard.setLabels();
                        GameController.endGame();
                    }
                    System.out.println("Read: " + message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * public void read() {
     * (new Thread() {
     * public void run() {
     * String message;
     * try {
     * System.out.println("Waiting for data");
     * while ((message = reader.readLine()) != null) {
     * System.out.println("read " + message);
     * }
     * } catch (Exception ex) {
     * ex.printStackTrace();
     * }
     * }
     * <p/>
     * }).start();
     * }
     */

    public void sendData(String data) {
        try {
            writer.println(data);
            writer.flush();
            System.out.println("Sended data: " + data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
