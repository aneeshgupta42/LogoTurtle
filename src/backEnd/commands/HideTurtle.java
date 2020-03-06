package backEnd.commands;

import controller.Control;

import java.util.List;

public class HideTurtle extends Command {
    private static final int NUMARGS = 0;

    public HideTurtle(){
        super();
        super.numberOfArgs= NUMARGS;
    }
    public HideTurtle(List<String> varargs, Control myControl)
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
    public double repeatCom() {
        return super.repeatCom();
    }
}
