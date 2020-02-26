package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class IsShowing extends Command {
    private final int NUMARGS = 0;
    private int return_value;
    public IsShowing(){
        super();
        super.numberOfArgs = NUMARGS;
    }

    public IsShowing(LinkedList<String> varargs, Control control)
    {
        super(varargs, control);
        // TO DO
        // WIll need to make functions in Turtle, and then in Control to do this.
//        return_value = control.tu ? 1:0;
    }

    @Override
    public String commandValueReturn() {
        return Integer.toString(return_value);
    }

    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
