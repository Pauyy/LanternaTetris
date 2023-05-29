import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.BasicTextImage;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;

public class Lanternis {

    public static void main(String[] args) throws IOException, InterruptedException {
        GameScreen gs = new GameScreen();
        gs.create();
        gs.close();
    }

    public static void test() throws IOException, InterruptedException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        Terminal terminal = factory.createTerminal();
        terminal.setCursorVisible(false);

        terminal.enterPrivateMode();
        TextGraphics textGraphics = terminal.newTextGraphics();
        TextGraphics clean = terminal.newTextGraphics();
        clean.setBackgroundColor(TextColor.ANSI.BLUE_BRIGHT);
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#cc7722"));
        textGraphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);

        TerminalSize size = terminal.getTerminalSize();
        textGraphics.drawRectangle(new TerminalPosition(size.getColumns() / 2 - 10/2 - 1, 0), new TerminalSize(12,22), '#');
        clean.drawLine(new TerminalPosition(size.getColumns() / 2 - 10/2, 0), new TerminalPosition(size.getColumns() / 2 + 10/2, 0), ' ');
        terminal.setCursorPosition(0,1);
        for(int i = 0 ; i < size.getColumns(); i++)
            terminal.putCharacter((char)('0' + (i % 10)));

        terminal.flush();
        Thread.sleep(10000);
        terminal.close();
    }
}
