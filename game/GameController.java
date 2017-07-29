import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ќтвечает за инициализацию игры, расположение кораблей,
 * обработку действий игрока.
 *
 * @author Zhenya on 22.04.2017.
 */
public class GameController {
    public static final int MAX_SHIPS = 10;
    public static final int BOARD_SIZE = 10;

    static public GameBoard firstPlayerBoard = new GameBoard(GameController.MAX_SHIPS, GameController.BOARD_SIZE);
    static public GameBoard secondPlayerBoard = new GameBoard(GameController.MAX_SHIPS, GameController.BOARD_SIZE);

    static public String myBoardData;
    static public String opBoardData;

    public Player firstPlayer;
    public Player secondPlayer;

    //  private GameBoard gameBoard;
    // private GameBoard firstPlayerBoard;
    static public Shooting shooting;
    public static GameServer gameServer;
    public static GameClient gameClient;

    public static String level;
    public static String mode;
    public static String status = "server";
    public static String queue = "first";
    public static String winner = "";
    public static boolean inGame = false;

    private GameLogic gameLogic = new GameLogic();

    /**
     * // private List<Cell> list = new ArrayList<>();
     * private List<EndGameListener> listeners = new ArrayList<EndGameListener>();
     * <p/>
     * public void addListener(EndGameListener toAdd) {
     * listeners.add(toAdd);
     * }
     * <p/>
     * <p/>
     * public boolean checkEndGame(){
     * if (shooting.killedShipsCount == MAX_SHIPS) {
     * winner = "You lost! Number of shots: " + shooting.shotCount;
     * endGame();
     * return true;
     * }
     * if (shooting.killedShipsCountUser == MAX_SHIPS) {
     * winner = "Congratulations! You won!" + "\n" + "Number of shots: " + shooting.shotCount;
     * endGame();
     * return true;
     * }
     * return false;
     * }
     * <p/>
     * public void endGame() {
     * System.out.println("End Game!!!");
     * <p/>
     * if (status == "server") {
     * gameServer.sendData("E" + winner);
     * }
     * // Notify everybody that may be interested.
     * for (EndGameListener hl : listeners)
     * hl.endGame();
     * }
     * <p/>
     * public GameController(){
     * this.addListener(gameClient);
     * }
     */
    public GameController(GameSpaceUI gameSpaceUI) {
    }

    public void startGame(String level, String mode, String status) {

        this.mode = mode;
        this.level = level;
        this.status = status;

        System.out.println(GameController.status);
        System.out.println(GameController.mode);
        System.out.println(GameController.level);

        if (this.mode.equals("computer")) {

            System.out.println("computer mode");
            //»нициализаци€ игровых полей
            for (int i = 0; i < 100; i++) {
                firstPlayerBoard = initGameBoard(firstPlayerBoard);
                secondPlayerBoard = initGameBoard(secondPlayerBoard);
                if ((firstPlayerBoard != null) && (secondPlayerBoard != null))
                    break;
            }

            myBoardData = GameBoard.makeMyBoardData(firstPlayerBoard);
            opBoardData = GameBoard.makeOpBoardData();
            //firstPlayer = new Player(firstPlayerBoard, secondPlayerBoard);

            //—оздание стратегии стрельбы по уровню
            shooting = new Shooting(firstPlayerBoard);
            if (level == "simple") {
                shooting.simpleShooting();
            }
            if (level == "hard") {
                shooting.smartShooting(firstPlayerBoard);
            }
        }

        //TODO: начало сетевой игры
        if (this.mode.equals("viaNet")) {
            shooting = new Shooting(firstPlayerBoard);
            System.out.println("viaNet mode");

            if (this.status.equals("server")) {
                System.out.println("viaNet server mode");
                for (int i = 0; i < 100; i++) {
                    firstPlayerBoard = initGameBoard(firstPlayerBoard);
                    secondPlayerBoard = initGameBoard(secondPlayerBoard);
                    if ((firstPlayerBoard != null) && (secondPlayerBoard != null))
                        break;
                }

                myBoardData = GameBoard.makeMyBoardData(firstPlayerBoard);
                opBoardData = GameBoard.makeOpBoardData();

                gameServer.sendData('M' + GameBoard.makeMyBoardData(secondPlayerBoard));
                gameServer.sendData('O' + GameBoard.makeOpBoardData());
                //firstPlayer = new Player(firstPlayerBoard, secondPlayerBoard);
                //secondPlayer = new Player(secondPlayerBoard, firstPlayerBoard);
            }

            if (status.equals("client")) {/**
             System.out.println("starting client");
             //gameClient = new GameClient();
             gameClient.setUpNetworking();
             //   gameClient.go();

             System.out.println("\n continue ...");*/
                gameClient.sendData("Receiving data ...");
            }
        }
        //     this.level = level;
        //     this.gameBoard=gameBoard;
        /**     this.gameBoard = new GameBoard(MAX_SHIPS, BOARD_SIZE);

         for (int i = 0; i < 20; i++) {
         this.gameBoard.clearGameBoard();
         try {
         // TODO : создание кораблей
         System.out.println("1 ship-4");
         this.gameLogic.createShip(this.gameBoard, 4, "D1");
         System.out.println("2 ship-3");
         this.gameLogic.createShip(this.gameBoard, 3, "C2");
         System.out.println("3 ship-3");
         this.gameLogic.createShip(this.gameBoard, 3, "C1");
         System.out.println("4 ship-2");
         this.gameLogic.createShip(this.gameBoard, 2, "B3");
         System.out.println("5 ship-2");
         this.gameLogic.createShip(this.gameBoard, 2, "B2");
         System.out.println("6 ship-2");
         this.gameLogic.createShip(this.gameBoard, 2, "B1");
         System.out.println("7 ship-1");
         this.gameLogic.createShip(this.gameBoard, 1, "A1");

         break;
         } catch (Exception e) {
         // ѕовтор€ем создание кораблей
         System.out.println("Ooops. Number of attemptions: " + i);
         }
         }
         if (this.gameBoard.getAllShips().size() != MAX_SHIPS) {
         System.out.println("Nu, ne sud'ba :(");
         return;
         }

         System.out.println();
         for (int y = 0; y < BOARD_SIZE; y++) {
         for (int x = 0; x < BOARD_SIZE; x++) {
         if (x == 0) {
         System.out.print(y + ":  ");
         }
         Cell cell = this.gameBoard.getCell(x, y);
         System.out.print((cell.occupied ? cell.ship.name : (cell.must_be_free_cell ? "xx" : "~~")) + " ");
         }
         System.out.println();
         }
         */
        //   this.shooting = new Shooting(this.gameBoard);
        this.shooting = shooting;
/**        if (level == "simple") {
 shooting.simpleShooting();
 }

 if (level == "hard") {
 shooting.smartShooting(gameBoard);
 }

 for (int i = 0; i < 100; i++) {
 if (shooting.killedShipsCount >= MAX_SHIPS) {
 System.out.print("THE END");
 break;
 }
 shooting.makeShoot(gameBoard);

 System.out.println();
 System.out.println(i + 1);
 for (int y = 0; y < BOARD_SIZE; y++) {
 for (int x = 0; x < BOARD_SIZE; x++) {
 if (x == 0) {
 System.out.print(y + ":  ");
 }
 Cell cell = gameBoard.getCell(x, y);
 System.out.print((cell.killed ? "kk" : (cell.checked ? "oo" : "~~")) + " ");
 }
 System.out.println();
 }

 }
 */
        /**
         int i=0;
         for (Cell cell : shooting.getUncheckedCells()){
         i++;
         System.out.print( i+" x="+cell.x+" y="+cell.y+"\n");
         }

         System.out.print("\n");
         System.out.print(shooting.getUncheckedCells().size() + "\n");

         list = shooting.simpleShooting();
         System.out.print("End \n");
         System.out.print(list.size() + "\n");
         i=0;
         for (Cell cell : list){
         i++;
         System.out.print( i+" x="+cell.x+" y="+cell.y+"\n");
         }

         System.out.print("\n");
         System.out.print(shooting.getUncheckedCells().size() + "\n");

         for (i=0; i<5; i++){
         Cell cell = shooting.getFirstSpot();
         System.out.print( i+" x="+cell.x+" y="+cell.y+"\n");
         }

         list.clear();
         list = shooting.killShip(3,4,gameBoard);

         System.out.print("\n");
         i=0;
         for (Cell cell : list){
         i++;
         System.out.print( i+" x="+cell.x+" y="+cell.y+"\n");
         }
         */
    }

    public GameBoard initGameBoard(GameBoard gameBoard) {
        gameBoard = new GameBoard(MAX_SHIPS, BOARD_SIZE);

        for (int i = 0; i < 20; i++) {
            gameBoard.clearGameBoard();
            try {
                // TODO : создание кораблей
                System.out.println("1 ship-4");
                this.gameLogic.createShip(gameBoard, 4, "D1");
                System.out.println("2 ship-3");
                this.gameLogic.createShip(gameBoard, 3, "C2");
                System.out.println("3 ship-3");
                this.gameLogic.createShip(gameBoard, 3, "C1");
                System.out.println("4 ship-2");
                this.gameLogic.createShip(gameBoard, 2, "B3");
                System.out.println("5 ship-2");
                this.gameLogic.createShip(gameBoard, 2, "B2");
                System.out.println("6 ship-2");
                this.gameLogic.createShip(gameBoard, 2, "B1");
                System.out.println("7 ship-1");
                this.gameLogic.createShip(gameBoard, 1, "A4");
                System.out.println("8 ship-1");
                this.gameLogic.createShip(gameBoard, 1, "A3");
                System.out.println("9 ship-1");
                this.gameLogic.createShip(gameBoard, 1, "A2");
                System.out.println("10 ship-1");
                this.gameLogic.createShip(gameBoard, 1, "A1");

                break;
            } catch (Exception e) {
                // ѕовтор€ем создание кораблей
                System.out.println("Ooops. Number of attemptions: " + i);
            }
        }
        if (gameBoard.getAllShips().size() != MAX_SHIPS) {
            System.out.println("Nu, ne sud'ba :(");
            return null;
        }

        System.out.println();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (x == 0) {
                    System.out.print(y + ":  ");
                }
                Cell cell = gameBoard.getCell(x, y);
                System.out.print((cell.occupied ? cell.ship.name : (cell.must_be_free_cell ? "xx" : "~~")) + " ");
            }
            System.out.println();
        }
        return gameBoard;
    }

    static public String endGame() {

        String message = new String();

        if (status.equals("server")) {
            System.out.println("In server option");
            if (winner.isEmpty()) {
                message = "Game was stopped!";
            } else {
                gameServer.sendData("E" + winner);
                if (winner.equals("first")) {
                    message = "Congratulations! You won!" + " Number of shots: " + shooting.shotCount;
                }
                if (winner.equals("second")) {
                    message = "You lost! Number of shots: " + shooting.shotCount;
                }
            }
        }

        if (status.equals("client")) {

            if (winner.isEmpty()) {
                message = "Game was stopped!";
            } else {
                if (winner.equals("second")) {
                    System.out.println("second player");
                    message = "Congratulations! You won!" + " Number of shots: ";// + shooting.shotCount;
                }
                if (winner.equals("first")) {
                    System.out.println("first player");
                    message = "You lost! Number of shots: ";// + shooting.shotCount;
                }
            }
        }

        Main.gameSpaceUI.endGame(message);
        GameController.inGame = false;
        GameController.myBoardData = null;
        GameController.opBoardData = null;
        return message;
    }

}
