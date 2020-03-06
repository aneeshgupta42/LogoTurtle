package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;
import java.util.List;

public class GetShape extends Command{
    private static final int NUMARGS = 0;
    private int index;

    public GetShape(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public GetShape(List<String> varargs, Control myControl)
    {
        super(varargs, myControl);
        super.numberOfArgs= NUMARGS;
        index = myControl.getShape();
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
