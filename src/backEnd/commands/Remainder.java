package backEnd.commands;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

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

        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));
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
