package UserInput;

import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

public class InputPoller{

    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            inputMap[e.getKeyCode()] = true;
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            inputMap[e.getKeyCode()] = false;
            hold[e.getKeyCode()] = 0;
        }
    };
    private final boolean[] inputMap = new boolean[256];
    private final int[] hold = new int[256];

    public InputPoller(Terminal terminal){
        ((JFrame) terminal).getContentPane().getComponents()[0].addKeyListener(keyListener);
    }

    public Object[] get(){
        //When ever the Inputs get requested (once per frame as expected) evry one that is aleready hold is hold for another consecutive frame
        //If the inputs get requestet two times per frame this will fail
        for(int i = 0; i < hold.length; i++)
            if(inputMap[i])
                hold[i]++;
        Object[] o = new Object[2];
        o[0] = inputMap;
        o[1] = hold;
        return o;
    }

}
