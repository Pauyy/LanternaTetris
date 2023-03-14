package Shapes;

public abstract class Tetrimino {

    public enum Orientation{
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    protected enum LastAction{
        RotateRight,
        RotateLeft,
        MoveRight,
        MoveLeft,
        Spawn,
        MoveDown
    }

    public abstract int getIdentifier();

    protected int offsetX;
    protected int offsetY;
    protected byte[][][] matrix;
    protected Orientation orientation = Orientation.NORTH;
    protected LastAction lastAction;

    public Tetrimino(){

    }

    public abstract Coordinates[] spawn();

    /**
     *
     * @return Coordinates where the piece would be rotated right
     */
    public Coordinates[] rotateRight() {
        Coordinates[] coordinates;
        switch (orientation) {
            case NORTH -> coordinates = getCoordinates(0,0, Orientation.EAST);
            case EAST -> coordinates = getCoordinates(0,0, Orientation.SOUTH);
            case SOUTH -> coordinates = getCoordinates(0,0, Orientation.WEST);
            case WEST -> coordinates = getCoordinates(0,0, Orientation.NORTH);
            default -> coordinates = null;
        }
        lastAction = LastAction.RotateRight;
        return coordinates;
    }

    /**
     *
     * @return Coordinates where the piece would be rotated Left
     */
    public Coordinates[] rotateLeft() {
        Coordinates[] coordinates;
        switch (orientation) {
            case NORTH -> coordinates = getCoordinates(0,0, Orientation.WEST);
            case EAST -> coordinates = getCoordinates(0,0, Orientation.NORTH);
            case SOUTH -> coordinates = getCoordinates(0,0, Orientation.EAST);
            case WEST -> coordinates = getCoordinates(0,0, Orientation.SOUTH);
            default -> coordinates = null;
        }
        lastAction = LastAction.RotateLeft;
        return coordinates;
    }

    /**
     *
     * @return Coordinates where the piece would be if it gets moved to the right
     */
    public Coordinates[] moveRight() {
        Coordinates[] coordinates = getCoordinates(+1, 0, this.orientation);
        lastAction = LastAction.MoveRight;
        return coordinates;
    }

    /**
     *
     * @return Coordinates where the piece would be if it gets moved to the left
     */
    public Coordinates[] moveLeft() {
        Coordinates[] coordinates = getCoordinates(-1, 0, this.orientation);
        lastAction = LastAction.MoveLeft;
        return coordinates;
    }

    /**
     *
     * @return Coordinates where the piece would be if it gets moved down
     */
    public Coordinates[] moveDown(){
        Coordinates[] coordinates = getCoordinates(0,-1, this.orientation);
        lastAction = LastAction.MoveDown;
        return coordinates;
    }

    /**
     * Updates the position of the Tetrimino according to the last action that was checked
     * This Method does not check if the position is valid
     */
    public void updatePosition(){
        switch (lastAction){
            case RotateRight:
                switch (this.orientation){
                    case NORTH -> this.orientation = Orientation.EAST;
                    case EAST ->  this.orientation = Orientation.SOUTH;
                    case SOUTH -> this.orientation = Orientation.WEST;
                    case WEST -> this.orientation = Orientation.NORTH;
                }
                break;
            case RotateLeft:
                switch (this.orientation){
                    case NORTH -> this.orientation = Orientation.WEST;
                    case EAST ->  this.orientation = Orientation.NORTH;
                    case SOUTH -> this.orientation = Orientation.EAST;
                    case WEST -> this.orientation = Orientation.SOUTH;
                }
                break;

            case MoveDown:
                this.offsetY -=1;
                break;
            case MoveLeft:
                this.offsetX -=1;
                break;
            case MoveRight:
                this.offsetX +=1;
                break;
            default:

        }
    }

    public void updatePosition(int offsetX, int offsetY, Orientation orientation){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.orientation = orientation;
    }

    protected Coordinates[] getCoordinates(int bonusOffsetX, int bonusOffsetY, Orientation orientation){
        int offsetX = this.offsetX + bonusOffsetX;
        int offsetY = this.offsetY + bonusOffsetY;
        return switch (orientation) {
            case NORTH -> calculateCoordinates(offsetX, offsetY, matrix[0]);
            case EAST -> calculateCoordinates(offsetX, offsetY, matrix[1]);
            case SOUTH -> calculateCoordinates(offsetX, offsetY, matrix[2]);
            case WEST -> calculateCoordinates(offsetX, offsetY, matrix[3]);
        };
    }

    public Coordinates[] getCoordinates(){
        return getCoordinates(0,0, this.orientation);
    }

    private Coordinates[] calculateCoordinates(int x, int y, byte[][] matrix){
        Coordinates[] coordinates = new Coordinates[4];
        byte pointer = 0;
        for(int row = 0; row < matrix.length; row++){
            for(int col = 0; col < matrix[0].length; col++){
                if(matrix[row][col] == 1){
                    coordinates[pointer++] = new Coordinates(x + col, y - row);
                }
                if(pointer == 4)
                    break;
            }
        }
        return coordinates;
    }
}