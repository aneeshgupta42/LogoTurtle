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

        double argOne = Double.parseDouble(varargs.get(0));
        minus = argOne * -1;

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(minus);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
