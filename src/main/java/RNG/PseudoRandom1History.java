package RNG;

import Shapes.*;

/**
 * PseudoRandom Generator with a History of 1 piece
 * If a piece is selected twice in a row it gets rerolled once
 * Present in Nintendo Tetris
 */
public class PseudoRandom1History extends RandomTetrominoGenerator{
    private int lastTetromino = 0;

    @Override
    public Tetrimino generateNewTetromino() {
        int num = random.nextInt(1,8);
        if(num == lastTetromino) {
            num = random.nextInt(1, 8);
        }
        lastTetromino = num;
        switch (num){
            case 1:
                return new O();
            case 2:
                return new I();
            case 3:
                return new T();
            case 4:
                return new L();
            case 5:
                return new J();
            case 6:
                return new S();
            case 7:
                return new Z();
            default:
                return null;
        }
    }
}
