package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class SetShape extends Command {
    private static final int NUMARGS = 1;

    public SetShape(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public SetShape(LinkedList<String> varargs, Control myControl)
    {
        super(varargs, myControl);
        super.numberOfArgs= NUMARGS;
        myControl.setTurtleVisible(true);
    }

    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(1);
        return ret;
    }

    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
