package game;

import java.util.List;

/**
 * Модель корабя
 *
 * @auhor Zhenya on 22.04.2017.
 */
public class Ship {
    public List<Cell> shipCells;
    public String name;

    public Ship() {
    }

    public Ship(List<Cell> shipCells, String name) {
        this.shipCells = shipCells;
        this.name = name;
    }
}
