package RNG;

import Shapes.*;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public abstract class RandomTetrominoGenerator {

    protected final Random random;
    private int notsorandombagcounter = 0;
    private Queue<Tetrimino> tetrominoQueue = new ArrayDeque<>();
    private int queueLength;

    public RandomTetrominoGenerator() {
        random = new Random();
        queueLength = 7;
        for(int i = 0; i < queueLength; i++){
            tetrominoQueue.add(generateNewTetromino());
        }
    }

    public RandomTetrominoGenerator(long seed) {
        random = new Random(seed);
    }

    public Queue<Tetrimino> getTetrominoQueue() {
        return tetrominoQueue;
    }

    public Tetrimino getNextTetromino() {
        Tetrimino tetrimino = tetrominoQueue.poll();
        tetrominoQueue.add(generateNewTetromino());
        return tetrimino;
    }

    public abstract Tetrimino generateNewTetromino();

    

}
