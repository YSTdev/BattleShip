/**
 * " вадратик" на поле
 *
 * @auhor Zhenya on 23.04.2017.
 */
public class Cell {
    public int x = 0;
    public int y = 0;

    public Ship ship = null;
    public boolean occupied = false;
    public boolean must_be_free_cell = false;
    public boolean checked = false;
    public boolean killed = false;
    public boolean marked = false;

    public Cell() {
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
