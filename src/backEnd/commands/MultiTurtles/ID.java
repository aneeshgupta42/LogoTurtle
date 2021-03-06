package backEnd.commands.MultiTurtles;

import backEnd.commands.Command;
import controller.Control;

import java.util.LinkedList;
import java.util.List;

public class ID extends Command {
    private double start;
    private double stop;
    private LinkedList<String> turtleID;
    private static final int NUMARGS = 0;
    private double id;

    public ID(){
        super();
    }

    public ID(List<String> varargs, Control control){
        super(varargs,control);
        super.numberOfArgs = NUMARGS;
        id = control.getRecentID();
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(id);
    }

}
