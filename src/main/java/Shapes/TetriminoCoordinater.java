package Shapes;

import Elements.Board;
import RNG.RandomTetriminoGenerator;
import UserInput.InputPoller;


public class TetriminoCoordinater {

    Tetrimino tetrimino;
    private final Board board;
    private final InputPoller input;
    private int frame = 0;
    private final RandomTetriminoGenerator rng;
    private int gravity = 12;

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
        Object[] completeInputs = input.get();
        boolean[] pressedButtons = (boolean[]) completeInputs[0];
        int[] holdTime = (int[]) completeInputs[1];

        Coordinates[] oldCoordinates = tetrimino.getCoordinates();
        Coordinates[] newCoordinates = null;

        //Find out new location
        if(pressedButtons[88] || pressedButtons[89] || pressedButtons[90] || pressedButtons[38]) {//Rotation?
            if((pressedButtons[38] || pressedButtons[88]) && (holdTime[38] == 1 || holdTime[88] == 1)){//↑ or x
                newCoordinates = tetrimino.rotateRight();
            } else if((pressedButtons[89] || pressedButtons[90]) && (holdTime[89] == 1 || holdTime[90] == 1)){// y or z
                newCoordinates = tetrimino.rotateLeft();
            }
        }
        updateTetriminoPositionIfValid(oldCoordinates, newCoordinates);

        oldCoordinates = tetrimino.getCoordinates();
        newCoordinates = null;
        if(pressedButtons[37] || pressedButtons[39]) {//Directional
            if(pressedButtons[37] && pressedButtons[39]){
                if(holdTime[37] < holdTime[39]) {
                    if ((holdTime[37] == 1 || (holdTime[37] > 16 && (((holdTime[37] + 2) % 6) == 0))))
                        newCoordinates = tetrimino.moveLeft();
                }else {
                    if ((holdTime[39] == 1 || (holdTime[39] > 16 && (((holdTime[39] + 2) % 6) == 0))))
                        newCoordinates = tetrimino.moveRight();
                }
            } else if(pressedButtons[37] && (holdTime[37] == 1 || (holdTime[37] > 16 && (((holdTime[37] + 2) % 6) == 0)))){
                //When Left is pressed for the first frame, move
                //If its hold for more than one frame we have to check if it reaches DAS
                //If it reaches DAS we have to check for AAR
                //But this looks still horrible
                newCoordinates = tetrimino.moveLeft();
                //                                                               DAS                        AAR
            } else if(pressedButtons[39] && (holdTime[39] == 1 || (holdTime[39] > 16 && (((holdTime[39] + 2) % 6) == 0)))){
                newCoordinates = tetrimino.moveRight();
            }
        }
        updateTetriminoPositionIfValid(oldCoordinates, newCoordinates);

        oldCoordinates = tetrimino.getCoordinates();
        newCoordinates = null;
        if(pressedButtons[32] && holdTime[32] == 1){
            hardDrop(tetrimino);
        }

        if(pressedButtons[40]){
            if(holdTime[40] == 1){
                newCoordinates = tetrimino.moveDown();
            } else if((holdTime[40] % (gravity / 2)) == 0){
                newCoordinates = tetrimino.moveDown();
            }
        }
        updateTetriminoPositionIfValid(oldCoordinates, newCoordinates);


        if(frame > gravity && tetrimino != null) { //When the frame exceeds the gravity the tetrimino falls one place down
            oldCoordinates = tetrimino.getCoordinates();
            newCoordinates = tetrimino.moveDown();
            boolean valid = updateTetriminoPositionIfValid(oldCoordinates, newCoordinates);
            if(!valid){
                //System.out.println("Invalid place after move down, lock piece");
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

    private boolean updateTetriminoPositionIfValid(Coordinates[] oldCoordinates, Coordinates[] newCoordinates){
        boolean valid = isNextPosValid(oldCoordinates, newCoordinates);
        if (valid) {
            tetrimino.updatePosition();
            updateBoard(oldCoordinates, newCoordinates);
        }
        return valid;
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
