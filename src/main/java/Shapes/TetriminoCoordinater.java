package Shapes;

import Elements.Board;
import RNG.RandomTetriminoGenerator;
import UserInput.InputPoller;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.Arrays;


public class TetriminoCoordinater {

    Tetrimino tetrimino;
    private final Board board;
    private final InputPoller input;
    private int frame = 0;
    private final RandomTetriminoGenerator rng;
    private int delay = 12;

    public TetriminoCoordinater(Board board, InputPoller input){
        this.board = board;
        rng = new RandomTetriminoGenerator();
        this.input = input;
    }

    public void setTetrimino(Tetrimino tetrimino){
        this.tetrimino = tetrimino;
    }

    public boolean process() {
        frame++;
        //Check if we have a current Piece
        if (tetrimino == null) {
            tetrimino = rng.getNewTetrimino();
            boolean valid = spawnTetrimino();
            if (!valid) {
                return true;
            }
        }
        //Process User Input
        KeyStroke keyStroke = input.poll();
        if (keyStroke != null) {
            Coordinates[] oldCoordinates = tetrimino.getCoordinates();
            Coordinates[] newCoordinates = null;
            switch (keyStroke.getCharacter()) {
                case 'd' -> newCoordinates = tetrimino.moveRight();
                case 'a' -> newCoordinates = tetrimino.moveLeft();
                case 'w' -> newCoordinates = tetrimino.rotateRight();
                case 'z' -> newCoordinates = tetrimino.rotateLeft();
                case 'k' -> hardDrop(tetrimino);
                case 's' -> delay = 1;
                default -> delay = 48;
            }
            boolean valid = isNextPosValid(oldCoordinates, newCoordinates);
            if (valid) {
                System.out.println(Arrays.toString(oldCoordinates) + " ; " + Arrays.toString(newCoordinates) + " ; " + tetrimino.lastAction + " ; " + tetrimino.orientation);
                tetrimino.updatePosition();
                updateBoard(oldCoordinates, newCoordinates);
            }
        } else {
            delay = 48;
        }

        if(frame > delay && tetrimino != null){ //48 Frames sind vorbei, das Tetrimino muss ein Feld runter

            Coordinates[] oldCoordinates = tetrimino.getCoordinates();
            Coordinates[] newCoordinates = tetrimino.moveDown();
            boolean valid = isNextPosValid(oldCoordinates, newCoordinates);
            if(valid) {
                tetrimino.updatePosition();
                updateBoard(oldCoordinates, newCoordinates);
            } else {
                System.out.println("Invalid place after move down, lock piece");
                tetrimino = null;
                checkAndClearLines();
            }
            frame = 0;
        }
        return false;
    }

    private boolean spawnTetrimino(){
        Coordinates[] coordinates = tetrimino.spawn();
        int[][] board = this.board.getBoardArray();
        boolean valid = true;
        for(Coordinates coordinate : coordinates)
            if (board[coordinate.getCol()][coordinate.getRow()] != 0) {
                //System.out.println("Row: " + coordinate.getRow() + " Col: " + coordinate.getCol() + " " + board[coordinate.getCol()][coordinate.getRow()]);
                valid = false;
                break;
            }

        if(valid) {
            for (Coordinates coordinate : coordinates)
                board[coordinate.getCol()][coordinate.getRow()] = tetrimino.getIdentifier();
            return true;
        }
        else {
            System.err.println("Das bedeutet Top out");
            return false;
        }
    }

    private void updateBoard(Coordinates[] oldCoordinates, Coordinates[] newCoordinates){
        int[][]board = this.board.getBoardArray();
        for(Coordinates coordinate : oldCoordinates)
            board[coordinate.getCol()][coordinate.getRow()] = 0;

        for(Coordinates coordinate : newCoordinates)
            board[coordinate.getCol()][coordinate.getRow()] = tetrimino.getIdentifier();
    }


    private boolean isNextPosValid(Coordinates[] oldPos, Coordinates[] newPos){
        if(oldPos == null || newPos == null)
            return false;

        for(Coordinates coordinate : newPos) {
            if (coordinate.getCol() >= 10 || coordinate.getCol() < 0)
                return false;
            if(coordinate.getRow() >= 22 || coordinate.getRow() < 0)
                return false;
        }

        int[][] board = this.board.getBoardArray();
        for(Coordinates coordinate : newPos) {
            if (board[coordinate.getCol()][coordinate.getRow()] != 0) {
                //The new Position is not empty, check if the new Position
                //Overlaps with the old one
                //Then it's fine because it can't obstruct itself (because it will move out of its way
                boolean overlapsSelf = false;
                for (Coordinates oldPosition : oldPos) {
                    if (coordinate.equals(oldPosition)) {
                        overlapsSelf = true;
                        break;
                    }
                }
                if(!overlapsSelf)
                    return false;
            }
        }
        return true;
    }

    private void hardDrop(Tetrimino tetrimino){
        Coordinates[] newCoordinates;
        Coordinates[] oldCoordinates;
        boolean valid;
        while(true){
            oldCoordinates = tetrimino.getCoordinates();
            newCoordinates = tetrimino.moveDown();
            valid = isNextPosValid(oldCoordinates, newCoordinates);
            if(valid) {
                updateBoard(oldCoordinates, newCoordinates);
                tetrimino.updatePosition();
            } else {
                break;
            }
        }
        this.tetrimino = null;
        checkAndClearLines();
    }

    /**
     * Checks if Lines have to be cleared and clears them
     */
    private void checkAndClearLines(){
        int[][] board = this.board.getBoardArray();
        int linesCleared = 0;
        for(int row = 0; row < 20; row++){

            int[] completeRow = parseRow(row, board);
            boolean clearLine = true;
            for(int col = 0; col < 10; col++){
                if(completeRow[col] == 0){
                    clearLine = false;
                    break;
                }
            }
            if(clearLine){
                for(int col = 0; col < 10; col++){
                    board[col][row] = 0;
                }
                linesCleared += 1;
            } else {
                moveDownRow(linesCleared, row, board);
            }

        }

    }
    
    private int[] parseRow(int row, int[][] board){
        int[] rowContent = new int[10];
        for(int col = 0; col < 10; col++){
            rowContent[col] = board[col][row];
        }
        return rowContent;
    }

    private void moveDownRow(int step, int row, int[][] board){
        if(step == 0)
            return;

        for(int col = 0; col < 10; col++){
            board[col][row-step] = board[col][row];
            board[col][row] = 0;
        }

    }

}
