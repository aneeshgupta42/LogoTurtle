package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;
import java.util.List;

public class SetShape extends Command {
    private static final int NUMARGS = 1;
    private int index;

    public SetShape(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public SetShape(List<String> varargs, Control control)
    {
        super(varargs, control);
        super.numberOfArgs= NUMARGS;
        index = Integer.parseInt(varargs.get(0));
        control.setShape(index);
    }

    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(index);
        return ret;
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
