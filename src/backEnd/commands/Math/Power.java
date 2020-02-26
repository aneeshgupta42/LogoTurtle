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
        System.out.println("pow args" + varargs.get(0) + " " + varargs.get(1));
        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));

        result = Math.pow(argOne, argTwo);

        System.out.println("Power is: " + result);
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(result);
    }
    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
