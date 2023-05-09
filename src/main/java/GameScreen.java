import Elements.Board;
import Graphic.Renderer;
import Shapes.TetriminoCoordinater;
import UserInput.InputPoller;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;



public class GameScreen {

    Terminal terminal;

    public GameScreen() {
        this.terminal = new DefaultTerminalFactory().createSwingTerminal();
        makeWindowVisible(terminal);
    }

    public void create() throws IOException, InterruptedException {
        terminal.setCursorVisible(false);

        Board board = new Board();
        Renderer renderer = new Renderer(terminal);
        InputPoller input = new InputPoller(terminal);
        TetriminoCoordinater tetriminoCoordinater = new TetriminoCoordinater(board, input);
        renderer.renderScreen();

        Thread.sleep(500);
        while(true) {
            boolean topOut = tetriminoCoordinater.process();
            renderer.renderBoard(board);

            Thread.sleep(16);

            if(topOut) {
                break;
            }
        }
        renderer.renderGameOver();
        Thread.sleep(5000);
    }

    public void close() throws IOException{
        terminal.close();
    }


    private void makeWindowVisible(Terminal terminal) {
        ((java.awt.Window) terminal).setVisible(true);
    }
}
