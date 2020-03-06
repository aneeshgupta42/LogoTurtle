package backEnd.commands;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

public class LessThan extends Command {
    private static final int NUMARGS = 2;
    private static final double TRUE = 1.0;
    private static final double FALSE = 0.0;
    private double booleanResult;

    public LessThan() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public LessThan(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));

        if(argOne < argTwo) {
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
