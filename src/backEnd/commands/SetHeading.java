package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;
import java.util.List;

public class SetHeading extends Command {

    private static final int NUMARGS = 1;
    private double angle_moved;
    public SetHeading(){
        super();
        super.numberOfArgs= NUMARGS;
    }
    public SetHeading(List<String> varargs, Control control)
    {
        super(varargs, control);
        super.numberOfArgs= NUMARGS;
        double current_angle = control.getTurtleAngle();
        double angle = Double.parseDouble(varargs.get(0));
        control.updateTurtle(0,0, (-current_angle+ angle), 0);
        angle_moved = angle - current_angle;
    }

    @Override
    public String commandValueReturn(){
        return Double.toString(angle_moved);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
