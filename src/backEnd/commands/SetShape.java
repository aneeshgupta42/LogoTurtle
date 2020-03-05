package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class SetShape extends Command {
    private static final int NUMARGS = 1;
    private int index;

    public SetShape(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public SetShape(LinkedList<String> varargs, Control myControl)
    {
        super(varargs, myControl);
        super.numberOfArgs= NUMARGS;
        index = Integer.parseInt(varargs.get(0));
        myControl.setShape(index);
    }

    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(index);
        return ret;
    }

    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
