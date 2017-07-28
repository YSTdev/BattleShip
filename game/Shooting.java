import java.util.*;

/**
 * Отвечает за стрельбу по кораблям.
 *
 * @author Zhenya on 25.05.2017.
 */
public class Shooting {
    private List<Cell> unchecked_cells; //В начале игры копирует все ссылки на ячейки из GameBoard, в процессе удаляются проверенные ячейки и гарантированно пустые (вокруг кораблей)
    private List<Cell> shootingStrategy = new ArrayList<>(); // Стратегия по которой выбираются ячейки для первого выстрела (до ранения корабля)
    private List<Cell> killingStrategy = new ArrayList<>(); // Стратегия по которой раненный корабль добивается
    private List<Cell> killedCells = new ArrayList<>(); // Хранит ссылки на ячейки, которые были "убиты" (обнуляется после разгрома корабля)
    private ArrayList<Integer> numbers;

    private Cell lastShot = null; //Хранит ссылку на ячейку, которая была ранена последней
    private Cell firstShot = null; // Хранит ссылку на ячейку, которая была ранена первой
    public int killedShipsCount = 0; // Счетчик разбитых кораблей, для определения конца игры
    public int killedShipsCountUser = 0;
    public int shotCount = 0;
    public int[] shipsAlive;
    public int[] opShipsAlive;
    //public int opponentCount = 0;

    public Shooting(GameBoard gameBoard) {
        this.unchecked_cells = new ArrayList<>(gameBoard.getBoardSize() * gameBoard.getBoardSize());

        shipsAlive = new int[4];
        shipsAlive[0]=4;
        shipsAlive[1]=3;
        shipsAlive[2]=2;
        shipsAlive[3]=1;

        opShipsAlive = new int[4];
        opShipsAlive[0]=4;
        opShipsAlive[1]=3;
        opShipsAlive[2]=2;
        opShipsAlive[3]=1;


        for (int y = 0; y < gameBoard.getBoardSize(); y++) {                //Копируем все ячейки из gameBoard в непровернные
            for (int x = 0; x < gameBoard.getBoardSize(); x++) {            //Один раз в начале игры
                Cell cell = gameBoard.getCell(x, y);
                unchecked_cells.add(cell);
            }
        }
    }

    /**
     *   Возвращает список непроверенных ячеек
     */
    public List<Cell> getUncheckedCells() {
        return this.unchecked_cells;
    }

    /**
     * Производит один выстрел
     *
     * @throws Exception
     */
    public boolean makeShoot(GameBoard gameBoard) {

        Cell cell;

        if (firstShot == null) {                        //Получаем ячейку по которой будем стрелять
            cell = getFirstSpot();                      //В зависимости от того, был ранен корабль или это первый выстрел
        } else {
            cell = getKillingSpot();
        }

        int i = 0;
        for (Cell cell2 : killingStrategy) {
            i++;
            System.out.print(i + " x=" + cell2.x + " y=" + cell2.y + "\n");
        }


        cell.checked = true;                            //Меняем статус checked ячейки на проверенный
        System.out.print("\n" + "cell x = " + cell.x + " cell y = " + cell.y + "\n");

        if (cell.occupied == true) {                    //Проверяем попадание
            cell.killed = true;
            killedCells.add(cell);
            cell.ship.shipCells.remove(cell);

            if (firstShot == null) {
                firstShot = cell;
            } else {
                lastShot = cell;
            }

            if (!cell.ship.shipCells.isEmpty()) {
                killingStrategy = killShip(cell.x, cell.y, gameBoard);
            } else {
                firstShot = null;
                lastShot = null;
                killedShipsCount++;
                detectFreeCells(killedCells, gameBoard);
                killedCells.clear();
            }
            return true;
        } else {
            return false;
        }

        /**     if (shootingStrategy.isEmpty()) {
         throw new Exception("All cells were checked!");
         }
         */

    }

    /**
     * Получить ячейку для первого выстрела
     */
    public Cell getFirstSpot() {
        //shootingStrategy = simpleShooting();

        Cell cell = new Cell();

        for (int i = 0; i < 100; i++) {
            cell = shootingStrategy.get(0);
            shootingStrategy.remove(cell);
            if (unchecked_cells.contains(cell)) {
                unchecked_cells.remove(cell);
                break;
            }
        }
        return cell;
    }

    /**
     * Получить ячейку для добивания корабля
     *
     * @return
     */
    public Cell getKillingSpot() {

        Cell cell = new Cell();

        int j = 0;
        for (Cell cell2 : killingStrategy) {
            j++;
            System.out.print(j + " x=" + cell2.x + " y=" + cell2.y + "\n");
        }

        for (int i = 0; i < 100; i++) {
            cell = killingStrategy.get(0);
            killingStrategy.remove(cell);
            if (unchecked_cells.contains(cell)) {
                unchecked_cells.remove(cell);
                break;
            }
        }
        if (!(firstShot == null))
            System.out.print(" firstShot x= " + firstShot.x + " firstShot y= " + firstShot.y + "\n");
        if (!(lastShot == null))
            System.out.print(" lastShot.x= " + lastShot.x + " lastShot y= " + lastShot.y + "\n");

        return cell;
    }

    /**
     * Простая стратегия выстрелов: все ячейки выбираются случайным образом
     */
    public List<Cell> simpleShooting() {

        shootingStrategy.clear();
        Random random = new Random();
        int size = unchecked_cells.size();
        numbers = new ArrayList<Integer>();

        int i = 0;
        while (numbers.size() < size) {
            //System.out.print("i = " + i + " numbers size " + numbers.size() + "\n");
            int x = random.nextInt(unchecked_cells.size());
            //System.out.print(x + " x\n");
            if (!numbers.contains(x)) {
                numbers.add(x);
                Cell cell = unchecked_cells.get(x);
                shootingStrategy.add(cell);
            }
            i++;
        }

        return shootingStrategy;
    }

    public List<Cell> smartShooting(GameBoard gameBoard) {
        shootingStrategy.clear();
        Random random = new Random();
        int size = unchecked_cells.size();
        numbers = new ArrayList<Integer>();

        for (int i = 0; i < gameBoard.getBoardSize(); i++) {
            for (int j = (i + 1) * (-1); j < gameBoard.getBoardSize(); j = j + 4)
                if ((i < gameBoard.getBoardSize()) && (i >= 0) && (j < gameBoard.getBoardSize()) && (j >= 0))
                    shootingStrategy.add(gameBoard.getCell(i, j));
        }

        for (int i = 0; i < gameBoard.getBoardSize(); i++) {
            for (int j = (i + 3) * (-1); j < gameBoard.getBoardSize(); j = j + 4)
                if ((i < gameBoard.getBoardSize()) && (i >= 0) && (j < gameBoard.getBoardSize()) && (j >= 0))
                    shootingStrategy.add(gameBoard.getCell(i, j));
        }

        while (numbers.size() < size) {
            int x = random.nextInt(unchecked_cells.size());
            if (!numbers.contains(x)) {
                numbers.add(x);
                Cell cell = unchecked_cells.get(x);
                if (!shootingStrategy.contains(cell))
                    shootingStrategy.add(cell);
            }
        }

        int i = 0;
        for (Cell cell2 : shootingStrategy) {
            i++;
            System.out.print(i + " x=" + cell2.x + " y=" + cell2.y + "\n");
        }

        return shootingStrategy;
    }

    /**
     * Стратегия для добивания корабля
     *
     * @param cell_x
     * @param cell_y
     * @param gameBoard
     * @return
     */
    public List<Cell> killShip(int cell_x, int cell_y, GameBoard gameBoard) {

        killingStrategy.clear();

        Cell cell;

        if (killedCells.size() == 1) {
            for (int i = -1; i <= 1; i = i + 2) {
                if ((cell_x + i < 10) && (cell_x + i >= 0) && (cell_y < 10) && (cell_y >= 0)) {
                    cell = gameBoard.getCell(cell_x + i, cell_y);
                    killingStrategy.add(cell);
                }
            }
            for (int i = -1; i <= 1; i = i + 2) {

                if ((cell_x < 10) && ((cell_x >= 0)) && (cell_y + i < 10) && (cell_y + i >= 0)) {
                    cell = gameBoard.getCell(cell_x, cell_y + i);
                    killingStrategy.add(cell);
                }
            }
        }

        if (killedCells.size() > 1) {
            int n = killedCells.size();
            Cell first_cell = new Cell(10, -1);
            Cell last_cell = new Cell(-1, 10);

            if (firstShot.x != lastShot.x) {
                for (Cell cell1 : killedCells) {
                    if (cell1.x < first_cell.x)
                        first_cell = cell1;
                }
                for (Cell cell1 : killedCells) {
                    if (cell1.x > last_cell.x)
                        last_cell = cell1;
                }
                if ((first_cell.x - 1 < 10) && ((first_cell.x - 1 >= 0)) && (first_cell.y < 10) && (first_cell.y >= 0))
                    killingStrategy.add(gameBoard.getCell(first_cell.x - 1, first_cell.y));
                if ((last_cell.x + 1 < 10) && ((last_cell.x + 1 >= 0)) && (last_cell.y < 10) && (last_cell.y >= 0))
                    killingStrategy.add(gameBoard.getCell(last_cell.x + 1, last_cell.y));
            }

            if (firstShot.y != lastShot.y) {
                for (Cell cell1 : killedCells) {
                    if (cell1.y > first_cell.y)
                        first_cell = cell1;
                }
                for (Cell cell1 : killedCells) {
                    if (cell1.y < last_cell.y)
                        last_cell = cell1;
                }
                if ((first_cell.x < 10) && ((first_cell.x >= 0)) && (first_cell.y + 1 < 10) && (first_cell.y + 1 >= 0))
                    killingStrategy.add(gameBoard.getCell(first_cell.x, first_cell.y + 1));
                if ((last_cell.x < 10) && ((last_cell.x >= 0)) && (last_cell.y - 1 < 10) && (last_cell.y - 1 >= 0))
                    killingStrategy.add(gameBoard.getCell(last_cell.x, last_cell.y - 1));
            }

            System.out.println();
            if (!(first_cell == null))
                System.out.print(" first_cell x= " + first_cell.x + " first_cell y= " + first_cell.y + "\n");
            if (!(last_cell == null))
                System.out.print(" last_cell x= " + last_cell.x + " last_cell y= " + last_cell.y + "\n");

/**
 for (int i = 0; i < 4 - n; i++) {
 if ((firstShot.x + step_x < 10)&&(firstShot.x + step_x >= 0) && (firstShot.y + step_y < 10)&&(firstShot.y + step_y >= 0)) {
 cell = gameBoard.getCell(firstShot.x + step_x, firstShot.y + step_y);
 killingStrategy.add(cell);
 }
 }
 for (int i = 0; i < 4 - n; i++) {
 if ((lastShot.x - step_x < 10)&&(lastShot.x - step_x >= 0) && (lastShot.y - step_y < 10)&&(lastShot.y - step_y >= 0)) {
 cell = gameBoard.getCell(lastShot.x - step_x, lastShot.y - step_y);
 killingStrategy.add(cell);
 }
 }
 */
        }
        return killingStrategy;
    }


    public void detectFreeCells(List<Cell> killedCells, GameBoard gameBoard) {
        for (Cell cell : killedCells) {
            int cell_x = cell.x;
            int cell_y = cell.y;
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    if (!((y + cell_y < 0) || (y + cell_y > gameBoard.getBoardSize() - 1) || (x + cell_x < 0) || (x + cell_x > gameBoard.getBoardSize() - 1))) {
                        Cell nearest_cell = gameBoard.getCell(x + cell_x, y + cell_y);
                        unchecked_cells.remove(nearest_cell);
                    }
                }
            }
        }
    }

    public boolean makeUserShot(int cCol, int cRow, String queue){

        Cell cell = new Cell();

        if (queue == "first"){
            cell = GameController.secondPlayerBoard.getCell(cCol, cRow);
        }
        if (queue == "second"){
            cell = GameController.firstPlayerBoard.getCell(cCol, cRow);
        }
        shotCount++;

        if ((!cell.marked)&&(!cell.checked)) {
            if (cell.occupied) {
                cell.checked = true;
                cell.killed = true;
                cell.ship.shipCells.remove(cell);

                if (cell.ship.shipCells.isEmpty()) {


                        if (queue =="first"){
                            killedShipsCountUser++;
                            System.out.println(killedShipsCountUser);
                            shipsAlive[cell.ship.shipSize-1]--;
                        }
                        if (queue == "second"){
                            killedShipsCount++;
                            System.out.println(killedShipsCount);
                            opShipsAlive[cell.ship.shipSize-1]--;
                        }
                }
            } else
                cell.checked = true;
            return true;
        }
        return false;
    }

    public void markCell(int cCol, int cRow){
        Cell cell = GameController.secondPlayerBoard.getCell(cCol,cRow);
        if (!cell.checked){
            if (!cell.marked)
                cell.marked = true;
            else
                cell.marked = false;
        }
    }

}
