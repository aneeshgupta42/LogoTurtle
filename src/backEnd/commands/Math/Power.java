package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;
import java.lang.Math;

public class Power extends Command {
    private static final int NUMARGS = 2;
    private double result;

    public Power() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Power(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double power = Double.parseDouble(varargs.get(0));
        double base = Double.parseDouble(varargs.get(1));
        result = Math.pow(base, power);

        System.out.println("The exponential result is: " + result);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(result);
        return ret;
    }
}
