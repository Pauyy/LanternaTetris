package Graphic;

import Elements.Board;
import Shapes.Coordinates;
import Shapes.T;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Arrays;


public class Renderer {

    private final Terminal terminal;
    private int[][] boardCache;

    public Renderer(Terminal terminal){
        this.terminal = terminal;

    }

    private int[][][] tetrominos = new int[][][]{
            {
                    {0,1,1,0},
                    {0,1,1,0}
            },
            {
                    {1,1,1,1},
                    {0,0,0,0}
            },
            {
                    {0,1,0,0},
                    {1,1,1,0}
            },
            {
                    {0,0,1,0},
                    {1,1,1,0}
            },
            {
                    {1,0,0,0},
                    {1,1,1,0}
            },
            {
                    {0,1,1,0},
                    {1,1,0,0}
            },
            {
                    {1,1,0,0},
                    {0,1,1,0}
            },
    };

    public void renderScreen() throws IOException {
        TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#ffa500"));
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#22ff22"));
        int rX, rY;
        TerminalSize size = terminal.getTerminalSize();
        rX = size.getColumns() / 2 - 12;

        textGraphics.drawLine(new TerminalPosition(rX,2), new TerminalPosition(rX,22), ' ');
        textGraphics.drawLine(new TerminalPosition(rX + 21,2), new TerminalPosition(rX + 21,22), ' ');
        textGraphics.drawLine(new TerminalPosition(rX,22), new TerminalPosition(rX + 21, 22), ' ');
        terminal.flush();
    }

    public void renderGameOver() throws IOException {
        TextGraphics textGraphics = terminal.newTextGraphics();
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#ffa500"));
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#22ff22"));

        int rX, rY;
        TerminalSize size = terminal.getTerminalSize();
        rX = size.getColumns() / 2 - 16;
        rY = size.getRows() / 2 - 5;

        textGraphics.drawRectangle(new TerminalPosition(rX, rY), new TerminalSize(30, 10), ' ');
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        textGraphics.fillRectangle(new TerminalPosition(rX + 1, rY + 1), new TerminalSize(28, 8),' ');

        textGraphics.setForegroundColor(TextColor.Factory.fromString("#ffa500"));
        textGraphics.putString(rX + 1 + 10, rY + 1 + 4, "Game Over");

        terminal.flush();
    }

    public void renderTetrominoPreview(int[] identifier) throws IOException {
        terminal.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);
        terminal.setBackgroundColor(TextColor.ANSI.BLUE);
        int x = 52;
        int y = 3;
        terminal.setCursorPosition(x, y);
        for(int i = 0; i < identifier.length; i++){ //every tetromino
            TextColor color = getColorForBlock(identifier[i]);
            terminal.setCursorPosition(x, y++);
            for(int j = 0; j < 2; j++) {
                terminal.setCursorPosition(x, y++);
                for (int k = 0; k < 4; k++) {
                    if (tetrominos[identifier[i]-1][j][k] == 0) {
                        terminal.setBackgroundColor(TextColor.ANSI.BLACK);
                    } else {
                        terminal.setBackgroundColor(color);
                    }
                    terminal.putCharacter(' ');
                    terminal.putCharacter(' ');
                }
            }
        }
        terminal.flush();
    }

    public void renderBoard(Board board) throws IOException {
        int rX, rY; //relative X an Y Coordiantes to the Board where 0/0 is the top left corner
        rX = terminal.getTerminalSize().getColumns() / 2 - 11;
        rY = 2;

        int[][] boardContent = board.getBoardArray();
        if(boardCache == null) {
            boardCache = copy(boardContent); //TODO Nicht immer das ganze Array kopieren sondern nur die Änderungen
            renderFullBoard(boardContent, rX, rY);
        } else {
            renderDiffedBoard(boardContent, boardCache, rX, rY);
            boardCache = copy(boardContent);
        }

        terminal.flush();
    }

    private void renderFullBoard(int[][] boardContent, int rX, int rY) throws IOException {
        for(int row = 0, boardRow = 19 ; row < 20; row++, boardRow--){
            terminal.setCursorPosition(rX + 0, rY + row);
            for(int col = 0 ; col < 10; col++){
                terminal.setBackgroundColor(getColorForBlock(boardContent[col][boardRow]));
                terminal.putCharacter(' ');
                terminal.putCharacter(' ');
            }
        }
    }

    private void renderDiffedBoard(int[][] boardContent, int[][] boardCache, int rX, int rY) throws IOException {
        for(int row = 0, boardRow = 19 ; row < 20; row++, boardRow--){//Only 20 rows are shown
            for(int col = 0; col < boardContent.length; col++){
                if(boardContent[col][boardRow] != boardCache[col][boardRow]){
                    terminal.setCursorPosition(rX + col * 2, rY + row);
                    terminal.setBackgroundColor(getColorForBlock(boardContent[col][boardRow]));
                    terminal.putCharacter(' ');
                    terminal.putCharacter(' ');
                }
            }
        }
    }

    private TextColor getColorForBlock(int val){
        return switch (val) {
            case 0 -> TextColor.ANSI.BLACK;
            case 1 -> TextColor.ANSI.YELLOW_BRIGHT;
            case 2 -> TextColor.ANSI.CYAN_BRIGHT;
            case 3 -> TextColor.ANSI.MAGENTA;
            case 4 -> TextColor.Factory.fromString("#FF971C");
            case 5 -> TextColor.ANSI.BLUE;
            case 6 -> TextColor.ANSI.GREEN;
            case 7 -> TextColor.ANSI.RED;
            case 8 -> TextColor.ANSI.BLACK_BRIGHT;
            default -> TextColor.Factory.fromString("#ff99b7");
        };
    }

    private int[][] copy(int[][]toCopy){
        int[][] result = new int[toCopy.length][toCopy[0].length];
        for(int row = 0; row < toCopy[0].length; row++)
            for(int col = 0 ; col < toCopy.length; col++)
                result[col][row] = toCopy[col][row];
        return result;
    }


}
