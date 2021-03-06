package backEnd.commands.Math;

import controller.Control;
import backEnd.commands.Command;
import java.lang.Math;
import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The Random class implements the Rand command functionality, following the Command superclass conventions.
 * It uses the java Math package to compute the value. The Random package was not used due to the naming
 * similarities.
 */

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
