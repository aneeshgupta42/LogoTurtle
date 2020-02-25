package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Minus extends Command {
    private static final int NUMARGS = 1;
    private double minus;

    public Minus() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Minus(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double a = Double.parseDouble(varargs.get(0));
        minus = a * -1;

        System.out.println("The negative is: " + minus);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(minus);
        return ret;
    }
}
