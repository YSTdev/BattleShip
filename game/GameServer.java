import com.sun.org.apache.xpath.internal.operations.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.lang.String;
import java.util.Iterator;

/**
 * Created by Zhenya on 24.07.2017.
 */
public class GameServer {

    ArrayList clientOutputStreams;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocket) {
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    if (message.charAt(0) == 'S'){
                        boolean successfulShot = GameController.shooting.makeUserShot(Character.getNumericValue(message.charAt(1)),Character.getNumericValue(message.charAt(2)), "second");

                        if (successfulShot&&(GameController.queue == "second")) {
                            GameController.myBoardData = GameBoard.makeMyBoardData(GameController.firstPlayerBoard);
                            GameController.gameServer.sendData('O' + GameBoard.changeBoardData(GameController.firstPlayerBoard));
                            GameController.queue = "first";
                        }
                    }
                    System.out.println("Read: " + message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void go() {

        (new Thread() {
            public void run() {
                clientOutputStreams = new ArrayList();
                try {
                    ServerSocket serverSocket = new ServerSocket(5000);


                    System.out.println("create a connection");


                    Socket clientSocket = serverSocket.accept();
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread t = new Thread(new ClientHandler(clientSocket));
                    t.start();
                    System.out.println("got a connection");


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void sendData(String data) {

        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(data);
                writer.flush();
                System.out.println("Sended data: " + data);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
