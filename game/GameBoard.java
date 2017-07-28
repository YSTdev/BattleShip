
import com.sun.org.apache.xpath.internal.operations.*;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель игрового поля
 *
 * @auhtor Zhenya on 23.04.2017.
 */
public class GameBoard {
    private int boardSize;
    private List<Ship> ships;
    private List<Cell> cells;

    public GameBoard(int shipCount, int size) {
        this.boardSize = size;
        this.ships = new ArrayList<>(shipCount);
        this.cells = new ArrayList<>(boardSize * boardSize);

        for (int y = 0; y < this.boardSize; y++) {
            for (int x = 0; x < this.boardSize; x++) {
                this.cells.add(new Cell(x, y));
            }
        }
    }

    public List<Ship> getAllShips() {
        return this.ships;
    }

    public List<Cell> getAllCells() {
        return this.cells;
    }

    public Cell getCell(int x, int y) {
        return this.cells.get(x + y * this.boardSize);
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * Сброс игрового поля
     */
    public void clearGameBoard() {
        for (Cell cell : cells) {
            cell.occupied = false;
            cell.must_be_free_cell = false;
            cell.ship = null;
        }
        this.ships.clear();
    }

    /**
     * Создание и размещенмие корабля на игровом поле
     */
    public Ship placeShipOnBoard(List<Cell> shipCells, String shipName, int shipSize) {
        Ship ship = new Ship(shipCells, shipName, shipSize);

        for (Cell cell : shipCells) {
            cell.occupied = true;
            cell.ship = ship;
        }

        for (Cell cell : shipCells) {
            int cell_x = cell.x;
            int cell_y = cell.y;
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (!((y + cell_y < 0) || (y + cell_y > boardSize - 1) || (x + cell_x < 0) || (x + cell_x > boardSize - 1))) {
                        Cell nearest_cell = getCell(x + cell_x, y + cell_y);
                        nearest_cell.must_be_free_cell = true;
                    }
                }
            }
        }

        this.ships.add(ship);
        return ship;
    }

    public static String makeMyBoardData(GameBoard gameBoard) {
        String boardData = new String("");
        Cell cell;

        for (int x = 0; x < GameController.BOARD_SIZE; x++) {
            for (int y = 0; y < GameController.BOARD_SIZE; y++) {
                cell = gameBoard.getCell(x, y);
                if (cell.ship != null) {
                    if (!cell.ship.shipCells.isEmpty()) {
                        if (cell.killed)
                            boardData += "k";
                        else if (cell.occupied)
                            boardData += "s";
                    } else
                        boardData += "d";
                } else {
                    if (cell.checked)
                        boardData += "c";
                    else
                        boardData += "e";
                }
            }
        }
        return boardData;
    }

    public static String makeOpBoardData() {
        String boardData = new String("");
        for (int x = 0; x < GameController.BOARD_SIZE; x++) {
            for (int y = 0; y < GameController.BOARD_SIZE; y++) {
                boardData += "e";
            }
        }

/**
 for (int x = 0; x < GameController.BOARD_SIZE; x++) {
 for (int y = 0; y < GameController.BOARD_SIZE; y++) {
 cell = gameBoard.getCell(x, y);

 if (cell.checked) {
 if (cell.killed) {
 if (!cell.ship.shipCells.isEmpty()) {
 boardData += "k";
 } else
 boardData += "d";
 } else
 boardData += "c";
 } else
 boardData +="e";

 if (cell.marked)
 boardData += "m";
 }
 }
 */
        return boardData;
    }

    public static String changeBoardData(GameBoard gameBoard) {

        String boardData = new String("");
        Cell cell;

        for (int x = 0; x < GameController.BOARD_SIZE; x++) {
            for (int y = 0; y < GameController.BOARD_SIZE; y++) {
                cell = gameBoard.getCell(x, y);


                if (cell.checked){
                    if (cell.killed){
                        if (!cell.ship.shipCells.isEmpty()){
                            boardData +="k";
                        }
                        else
                            boardData +="d";
                    }
                    else
                        boardData +="c";
                }
                else
                    boardData +="e";

                if (cell.marked)
                    boardData +="m";


/**
 String boardData;
 Cell cell;
 cell = gameBoard.getCell(cCol, cRow);

 if (cell.checked) {
 if (cell.killed) {
 if (!cell.ship.shipCells.isEmpty()) {
 boardData = Data.substring(0,cRow * GameController.BOARD_SIZE + cCol)+ 'k'+ Data.substring(cRow * GameController.BOARD_SIZE + cCol + 1);
 } else
 boardData = Data.substring(0,cRow * GameController.BOARD_SIZE + cCol)+ 'd'+ Data.substring(cRow * GameController.BOARD_SIZE + cCol + 1);
 } else
 boardData = Data.substring(0,cRow * GameController.BOARD_SIZE + cCol)+ 'c'+ Data.substring(cRow * GameController.BOARD_SIZE + cCol + 1);
 } else
 boardData = Data.substring(0,cRow * GameController.BOARD_SIZE + cCol)+ 'e'+ Data.substring(cRow * GameController.BOARD_SIZE + cCol + 1);

 if (cell.marked)
 boardData = Data.substring(0,cRow * GameController.BOARD_SIZE + cCol)+ 'm'+ Data.substring(cRow * GameController.BOARD_SIZE + cCol + 1);*/
            }
        }
        System.out.println(boardData);
        return boardData;
    }
}
