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

        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));
        remainder = argOne % argTwo;

        System.out.println("The remainder is: " + remainder);
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(remainder);
    }
    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
