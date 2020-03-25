package backEnd.commands.Math;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The Tangent class implements the TAN command functionality, following the Command superclass conventions.
 * It uses the java Math package to compute the value.
 */

public class Tangent extends Command {
    private static final int NUMARGS = 1;
    private double tanResult;

    public Tangent() {
        super();
        super.numberOfArgs= NUMARGS;
    }

    public Tangent(List<String> varargs, Control control){
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        tanResult = Math.tan((Math.toRadians(argOne)));

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(tanResult);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
