package backEnd.commands.Boolean;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The Not class implements the NOT command functionality, following the Command superclass conventions.
 * Note: All booleans return double values, rather than booleans.
 */
public class Not extends Command {
    private static final int NUMARGS = 1;
    private static final double TRUE = 1.0;
    private static final double FALSE = 0.0;
    private double booleanResult;

    public Not() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Not(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        if(argOne == 0) {
            booleanResult = TRUE;
        } else {
            booleanResult = FALSE;
        }
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(booleanResult);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
