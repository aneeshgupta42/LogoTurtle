package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class SetHeading extends Command {

    private static final int NUMARGS = 1;
    private double angle_moved;
    public SetHeading(){
        super();
        super.numberOfArgs= NUMARGS;
    }
    public SetHeading(LinkedList<String> varargs, Control myControl)
    {
        super(varargs, myControl);
        super.numberOfArgs= NUMARGS;
        double current_angle = myControl.getTurtleAngle();
        double angle = Double.parseDouble(varargs.get(0));
//        angle = 90.00 - angle;
        myControl.updateTurtle(0,0, (-current_angle+ angle), 0);
        angle_moved = angle - current_angle;
    }

    @Override
    public String commandValueReturn(){
        return Double.toString(angle_moved);
    }

    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
