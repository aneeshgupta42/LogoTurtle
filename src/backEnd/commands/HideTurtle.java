package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class HideTurtle extends Command {
    private static final int NUMARGS = 0;

    public HideTurtle(){
        super();
        super.numberOfArgs= NUMARGS;
    }
    public HideTurtle(LinkedList<String> varargs, Control myControl)
    {
        super(varargs, myControl);
        super.numberOfArgs= NUMARGS;
        myControl.setTurtleVisible(false);
    }
    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(0);
        return ret;
    }

    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
