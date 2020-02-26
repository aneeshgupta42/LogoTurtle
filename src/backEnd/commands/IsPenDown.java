package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class IsPenDown extends Command {
    private final int NUMARGS = 0;
    private int return_value;
    public IsPenDown(){
        super();
        super.numberOfArgs = NUMARGS;
    }

    public IsPenDown(LinkedList<String> varargs, Control control)
    {
        super(varargs, control);
        return_value = control.isPenDown() ? 1:0;
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
