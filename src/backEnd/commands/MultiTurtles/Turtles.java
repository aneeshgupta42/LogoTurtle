package backEnd.commands.MultiTurtles;

import backEnd.commands.Command;
import controller.Control;

import java.util.LinkedList;
import java.util.List;

public class Turtles extends Command {
    private int numTurtles;
    private static final int NUMARGS = 0;

    public Turtles()
    {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Turtles(List<String> varargs, Control control){
        super(varargs,control);
        super.numberOfArgs = NUMARGS;
        numTurtles = control.getNumTurtles();
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }

    public String commandValueReturn() {
        return Integer.toString(numTurtles);
    }
}
