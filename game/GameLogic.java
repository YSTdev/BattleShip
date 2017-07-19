package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Zhenya on 22.04.2017.
 */
public class GameLogic {

    /**
     * Создание и расположение кораблая на игровом поле
     */
    public void createShip(GameBoard gameBoard, int shipSize, String shipName) throws Exception {
        List<Cell> shipCells = getShipLocation(gameBoard, shipSize);
        if (shipCells.isEmpty()) {
            throw new Exception("Can't place s ship!");
        }
        gameBoard.placeShipOnBoard(shipCells, shipName);
    }

    /**
     * Создает позиции для корабля
     */
    private List<Cell> getShipLocation(GameBoard gameBoard, int shipSize) {
        Random random = new Random(System.currentTimeMillis());

        List<Cell> shipCells = new ArrayList<>();
        for (int i = 0; i <= 300; i++) {
            shipCells.clear();
            int flag = 0;
            int direction = (int) (Math.random() * 3);

            int x = random.nextInt(gameBoard.getBoardSize());
            int y = random.nextInt(gameBoard.getBoardSize());

            Cell cell = gameBoard.getCell(x, y);
            if ((!cell.must_be_free_cell) && (!cell.occupied)) {
                flag++;
                shipCells.add(cell);

                for (int z = 0; z < shipSize - 1; z++) {
                    if (direction < 1) {
                        x++;
                    } else {
                        y++;
                    }
                    Cell nextCell = gameBoard.getCell(x, y);
                    if ((!nextCell.must_be_free_cell) && (!nextCell.occupied) && (x < gameBoard.getBoardSize()) && (y < gameBoard.getBoardSize())) {
                        flag++;
                        shipCells.add(nextCell);
                    }
                }
            }
            if (flag == shipSize) {
                break;
            }
        }
        return shipCells;
    }

    /**
     * проверяет количество "живых кораблей"
     */
    public void checkStillAlive() {
    }

    /**
     * завершает игру
     */
    public void finishGame() {
    }
}
