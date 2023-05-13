package RNG;

import Shapes.*;

public class NotSoRandom7Bag extends RandomTetrominoGenerator{
    private int notsorandombagcounter = 0;
    @Override
    public Tetrimino generateNewTetromino() {
        return switch (notsorandombagcounter++ % 7 + 1) {
            case 1 -> new O();
            case 2 -> new I();
            case 3 -> new T();
            case 4 -> new L();
            case 5 -> new J();
            case 6 -> new S();
            case 7 -> new Z();
            default -> null;
        };
    }
}
