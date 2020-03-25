package backEnd.commands.TurtleQueries;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The isShowing class implements the isShowing command functionality, following the Command superclass conventions.
 * Note: The result is returned as an integer, representing a true or false evaluation (1 or 0).
 */
public class IsShowing extends Command {
    private final int NUMARGS = 0;
    private int returnBoolean;

    public IsShowing() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public IsShowing(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        returnBoolean = control.findTurtleVisibility() ? 1:0;
    }

    @Override
    public String commandValueReturn() {
        return Integer.toString(returnBoolean);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
