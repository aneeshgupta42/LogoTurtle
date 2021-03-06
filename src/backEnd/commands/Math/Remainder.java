package backEnd.commands.Math;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The Remainder class implements the Remainder command functionality, following the Command superclass conventions.
 */

public class Remainder extends Command {
    private static final int NUMARGS = 2;
    private double remainder;

    public Remainder() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Remainder(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(1));
        double argTwo = Double.parseDouble(varargs.get(0));
        remainder = argOne % argTwo;

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(remainder);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
