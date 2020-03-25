package backEnd.commands.TurtleQueries;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The XCoordinate class implements the XCoordinate command functionality, following the Command superclass conventions.
 */
public class XCoordinate extends Command {
    private static final int NUMARGS = 0;
    private double returnXCord;

    public XCoordinate() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public XCoordinate(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        returnXCord = control.getTurtleRelativeXPos();
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(returnXCord);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
