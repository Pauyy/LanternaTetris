package UserInput;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Stack;

public class InputPoller implements Runnable{

    private final Terminal terminal;

    private Stack<KeyStroke> stack;
    private Thread selfThread;
    private boolean running;

    public InputPoller(Terminal terminal){
        this.terminal = terminal;
        stack = new Stack<>();
        selfThread = new Thread(this);
        selfThread.start();
        running = true;
    }


    public KeyStroke[] get(){
        if(stack.isEmpty())
            return null;
        KeyStroke[] keyStrokes = new KeyStroke[2];
        boolean directional = false;
        boolean rotation = false;
        for(KeyStroke ks : stack){
            if(ks.getCharacter() != null){
                if(ks.getCharacter() == ' '){
                    keyStrokes[1] = new KeyStroke(KeyType.F19);
                    directional = true;
                } else {
                    keyStrokes[0] = ks;
                    rotation = true;
                }
            } else if(ks.getKeyType() == KeyType.ArrowUp){
                keyStrokes[0] = KeyStroke.fromString("x");
                rotation = true;
            } else {
                keyStrokes[1] = ks;
                directional = true;
            }
            if(rotation && directional)
                break;
        }
        if(!rotation)
            keyStrokes[0] = KeyStroke.fromString(" ");
        if(!directional)
            keyStrokes[1] = new KeyStroke(KeyType.Escape);

        stack.clear();
        return keyStrokes;
    }

    @Override
    public void run() {
        while (running) {
            KeyStroke polled;
            try {
                polled = terminal.pollInput();
                if (polled != null)
                    stack.add(polled);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        running = false;
    }

}
