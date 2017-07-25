package game;

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

    public void go(){
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    private void setUpNetworking(){
        try {
            sock = new Socket("127.0.0.1", 5000);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("networking established");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public class IncomingReader implements Runnable{
        public void run(){
            String message;
            try {
                while ((message = reader.readLine())!=null){
                    System.out.println("read " + message);
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
