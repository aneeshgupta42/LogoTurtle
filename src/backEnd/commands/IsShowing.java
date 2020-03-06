package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.List;

public class IsShowing extends Command {
    private final int NUMARGS = 0;
    private int returnBoolean;

    public IsShowing() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public IsShowing(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        returnBoolean = control.findTurtleVisibility() ? 1:0;

        System.out.println("Is the turtle visible: " + returnBoolean);
    }

    @Override
    public String commandValueReturn() {
        return Integer.toString(returnBoolean);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
