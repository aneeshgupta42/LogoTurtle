package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class Home extends Command{
    private final int NUMARGS = 0;
    private int distance;
    public Home(){
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Home(LinkedList<String> varargs, Control control){
        super(varargs, control);
        control.turtleHome(false);
        distance = control.getTurtleDistance();
    }

    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(distance);
        return ret;
    }

}
