package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class ArcTangent extends Command {
    private static final int NUMARGS = 1;
    private double arcTanResult;

    public ArcTangent() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public ArcTangent(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        arcTanResult = Math.atan(Math.toRadians(argOne));

        System.out.println("Arctangent is: " + arcTanResult);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(arcTanResult);
        return ret;
    }
}
