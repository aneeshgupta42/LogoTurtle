package backEnd.commands.Boolean;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class And extends Command {
    private static final int NUMARGS = 2;
    private static final double TRUE = 1.0;
    private static final double FALSE = 0.0;
    private double booleanResult;

    public And() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public And(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble((varargs.get(1)));

        if(argOne != 0 && argTwo != 0) {
            booleanResult = TRUE;
        } else {
            booleanResult = FALSE;
        }

        System.out.println("The And result is: " + booleanResult);
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(booleanResult);
    }

    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
