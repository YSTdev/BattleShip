
//import game.Cell;
//import game.GameBoard;
//import game.GameController;

import java.util.ArrayList;

/**
 * Created by Zhenya on 24.07.2017.
 */
public class Player {
    private String name;
    public static GameBoard myGameboard;
    public static GameBoard opponentGameBoard;
    private String myBoardData;
    private String opBoardData;

    public Player(GameBoard myGameBoard, GameBoard opponentGameBoard, String myBoardData, String opBoardData) {

        this.myBoardData = myBoardData;
        this.opBoardData = opBoardData;
        this.myGameboard = myGameBoard;
        this.opponentGameBoard = opponentGameBoard;
    }

    public void setName(String name) {
        this.name = name;
    }

}
