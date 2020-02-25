package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Tangent extends Command {
    private static final int NUMARGS = 1;
    private double tanResult;

    public Tangent() {
        super();
        super.numberOfArgs= NUMARGS;
    }

    public Tangent(LinkedList<String> varargs, Control control){
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        tanResult = Math.tan((Math.toRadians(argOne)));

        System.out.println("Tan is: " + tanResult);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(tanResult);
        return ret;
    }
}
