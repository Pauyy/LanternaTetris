package RNG;

import Shapes.*;

public class PseudoRandom extends RandomTetrominoGenerator {



    public Tetrimino generateNewTetromino(){
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

}
