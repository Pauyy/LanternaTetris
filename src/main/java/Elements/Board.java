package Elements;

public class Board {

    private final int[][] board;

    public Board(){
        board = new int[10][22];
    }

    public int[][] getBoardArray(){
        return board;
    }

    //SDPC setup with Garbage to make a not manipulated O Tetrimino clear a line
    public void fillBoardWithTestTetriminos(){
        board[0][0] = 1;
        board[1][0] = 1;
        board[0][1] = 1;
        board[1][1] = 1;

        board[0][2] = 7;
        board[0][3] = 7;
        board[1][3] = 7;
        board[1][4] = 7;

        board[2][0] = 6;
        board[3][0] = 6;
        board[3][1] = 6;
        board[4][1] = 6;

        board[5][1] = 5;
        board[6][1] = 5;
        board[7][1] = 5;
        board[7][0] = 5;

        board[7][2] = 4;
        board[8][2] = 4;
        board[8][1] = 4;
        board[8][0] = 4;

        board[9][0] = 2;
        board[9][1] = 2;
        board[9][2] = 2;
        board[9][3] = 2;

        board[1][2] = 8;
        board[2][2] = 8;
        board[6][2] = 8;
        board[3][2] = 8;
    }
}
