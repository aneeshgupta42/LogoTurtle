package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;
import java.lang.Math;
import java.util.List;

public class Random extends Command {
    private static final int NUMARGS = 1;
    private double random;

    public Random() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Random(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        random = Math.random() * argOne;

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(random);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
