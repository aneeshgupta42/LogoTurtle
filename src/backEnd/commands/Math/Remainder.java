package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Remainder extends Command {
    private static final int NUMARGS = 2;
    private double remainder;

    public Remainder() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Remainder(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double a = Double.parseDouble(varargs.get(0));
        double b = Double.parseDouble(varargs.get(1));
        remainder = b % a;

        System.out.println("The remainder is: " + remainder);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(remainder);
        return ret;
    }
}
