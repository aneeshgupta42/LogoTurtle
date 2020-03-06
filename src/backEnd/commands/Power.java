package backEnd.commands;

import controller.Control;
import backEnd.commands.Command;
import java.lang.Math;
import java.util.List;

public class Power extends Command {
    private static final int NUMARGS = 2;
    private double result;

    public Power() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Power(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));

        result = Math.pow(argOne, argTwo);

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(result);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
