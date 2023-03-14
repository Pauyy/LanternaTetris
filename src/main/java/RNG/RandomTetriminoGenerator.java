package RNG;

import Shapes.*;

import java.util.Random;

public class RandomTetriminoGenerator {

    private final Random random;
    private int notsorandombagcounter = 0;

    public RandomTetriminoGenerator() {
        random = new Random();
    }

    public RandomTetriminoGenerator(long seed) {
        random = new Random(seed);
    }

    public Tetrimino getNewTetrimino(){
        int num = random.nextInt(1,8);
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

    public Tetrimino notSoRandomBag(){
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
