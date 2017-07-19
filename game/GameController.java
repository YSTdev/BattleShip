package game;

import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Отвечает за инициализацию игры, расположение кораблей,
 * обработку действий игрока.
 *
 * @author Zhenya on 22.04.2017.
 */
public class GameController {
    public static final int MAX_SHIPS = 7;
    public static final int BOARD_SIZE = 10;

    private GameLogic gameLogic = new GameLogic();
  //  private GameBoard gameBoard;
   // private GameBoard myGameBoard;
    private Shooting shooting;
  //  private String level;

    private List<Cell> list = new ArrayList<>();

    public void startGame(String level, GameBoard gameBoard, Shooting shooting) {

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
                // Повторяем создание кораблей
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
*/
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
                this.gameLogic.createShip(gameBoard, 1, "A1");

                break;
            } catch (Exception e) {
                // Повторяем создание кораблей
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
}
