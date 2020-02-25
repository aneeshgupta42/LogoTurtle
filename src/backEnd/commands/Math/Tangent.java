package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;

import java.util.LinkedList;

public class Tangent extends Command {
    private double myResult;
    private final int NUMARGS = 1;

    public Tangent(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public Tangent(LinkedList<String> varargs, Control control){
        super(varargs, control);
        double myAngle = Double.parseDouble(varargs.get(0));
        myResult = Math.tan((Math.toRadians(myAngle)));
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(myResult);
        return ret;
    }
}
