package backEnd.commands.Boolean;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Equal extends Command {
    private static final int NUMARGS = 2;
    private static final double TRUE = 1.0;
    private static final double FALSE = 0.0;
    private double booleanResult;

    public Equal() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Equal(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        System.out.println("Args" + varargs.get(0) + varargs.get(1));
        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));

        if(argOne == argTwo) {
            booleanResult = TRUE;
        } else {
            booleanResult = FALSE;
        }

        System.out.println("The Equals result is: " + booleanResult);
    }

    @Override
    public String commandValueReturn() {
        System.out.println("EQual" + Double.toString(booleanResult));
        return Double.toString(booleanResult);
    }

    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
