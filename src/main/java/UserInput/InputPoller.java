package UserInput;

import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputPoller{

    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() > 255)
                return;
            inputMap[e.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() > 255)
                return;
            inputMap[e.getKeyCode()] = false;
            hold[e.getKeyCode()] = 0;
        }
    };
    private final boolean[] inputMap = new boolean[256];//Determine which Buttons was pressed
    private final int[] hold = new int[256];//Determine for how long which Button was pressed

    public InputPoller(Terminal terminal){
        ((JFrame) terminal).getContentPane().getComponents()[0].addKeyListener(keyListener);
    }

    public Object[] get(){
        //When ever the Inputs get requested (once per frame as expected) every one that is already hold is hold for another consecutive frame
        //If the inputs get requested two times per frame this will fail
        for(int i = 0; i < hold.length; i++)
            if(inputMap[i])
                hold[i]++;
        Object[] o = new Object[2];
        o[0] = inputMap;
        o[1] = hold;
        return o;
    }

}
