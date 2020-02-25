package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;
import java.lang.Math;

public class Random extends Command {
    private static final int NUMARGS = 1;
    private double random;

    public Random() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Random(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double max = Double.parseDouble(varargs.get(0));
        random = Math.random() * max;
        // insert check if max is too big ?

        System.out.println("The random number is: " + random);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(random);
        return ret;
    }
}
