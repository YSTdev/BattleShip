package game;

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
    public Ship placeShipOnBoard(List<Cell> shipCells, String shipName) {
        Ship ship = new Ship(shipCells, shipName);

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
}
