package Shapes;

import static Shapes.Tetrimino.Orientation.SOUTH;

public class S extends Tetrimino{

    public S(){
                matrix = new byte[][][]
                {
                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 1, 1, 0, 0},
                         {0, 0, 1, 1, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},

                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 1, 0, 0, 0},
                         {0, 0, 0, 1, 1, 0, 0},
                         {0, 0, 0, 0, 1, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},

                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 1, 1, 0, 0},
                         {0, 0, 1, 1, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},

                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 1, 0, 0, 0},
                         {0, 0, 0, 1, 1, 0, 0},
                         {0, 0, 0, 0, 1, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},
                };
    }

    @Override
    public int getIdentifier() {
        return 6;
    }

    @Override
    public Coordinates[] spawn() {
        offsetX = 2;
        offsetY = 22;
        orientation = SOUTH;
        return getCoordinates(0,0, orientation);
    }
}
