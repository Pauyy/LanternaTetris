package UserInput;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class InputPoller {

    private final Terminal terminal;

    public InputPoller(Terminal terminal){
        this.terminal = terminal;
    }


    public KeyStroke poll(){
        try {
            return terminal.pollInput();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
