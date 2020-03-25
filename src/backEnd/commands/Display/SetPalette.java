package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The setPalette class implements the setPalette command functionality, following the Command superclass conventions.
 */
public class SetPalette extends Command {
    private static final int NUMARGS = 4;
    private int userIndex, red, green, blue;

    public SetPalette() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public SetPalette(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        userIndex = Integer.parseInt(varargs.get(3));
        red = Integer.parseInt(varargs.get(2));
        green = Integer.parseInt(varargs.get(1));
        blue = Integer.parseInt(varargs.get(0));

        control.switchPaletteColor(userIndex, red, green, blue);
    }

    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(userIndex);
        return ret;
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
