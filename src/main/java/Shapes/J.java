package Shapes;

import static Shapes.Tetrimino.Orientation.SOUTH;

public class J extends Tetrimino{

    public J(){
                matrix = new byte[][][]
                {
                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 1, 0, 0, 0, 0},
                         {0, 0, 1, 1, 1, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},

                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 1, 1, 0, 0},
                         {0, 0, 0, 1, 0, 0, 0},
                         {0, 0, 0, 1, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},

                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 1, 1, 1, 0, 0},
                         {0, 0, 0, 0, 1, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},

                        {{0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 1, 0, 0, 0},
                         {0, 0, 0, 1, 0, 0, 0},
                         {0, 0, 1, 1, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0}},
                };
    }

    @Override
    public int getIdentifier() {
        return 5;
    }

    @Override
    public Coordinates[] spawn() {
        offsetX = 2;
        offsetY = 22;
        orientation = SOUTH;
        return getCoordinates(0,0, orientation);
    }
}
