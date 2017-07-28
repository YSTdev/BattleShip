import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhenya on 25.07.2017.
 */
public class GameClient {

    BufferedReader reader;
    PrintWriter writer;
    Socket sock;
/**
    private List<EndGameListener> listeners = new ArrayList<EndGameListener>();

    public void addListener(EndGameListener toAdd) {
        listeners.add(toAdd);
    }
    public void endGame() {
        System.out.println("End Game!!!");
        // Notify everybody that may be interested.
        for (EndGameListener hl : listeners)
            hl.endGame();
    }
    public void clientEndGame() {
        System.out.println("End Game!!!");
        for (EndGameListener hl : listeners)
            hl.endGame();
    }
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
                    go();
                } catch (Exception ex) {
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
                    if (message.charAt(0) == 'O') {
                        GameController.opBoardData = message.substring(1);
                        System.out.println("Read opData: " + message);
                    }
                    if (message.charAt(0) == 'M') {
                        GameController.myBoardData = message.substring(1);
                        System.out.println("Read myData: " + message);
                    }
                    if (message.charAt(0) == 'E') {
                        GameController.winner = message.substring(1);
                        Main.gameSpaceUI.endGame();
                        System.out.println("End game: " + GameController.winner);
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
