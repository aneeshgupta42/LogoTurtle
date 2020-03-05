package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class GetShape extends Command{
    private static final int NUMARGS = 0;
    private int index;

    public GetShape(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public GetShape(LinkedList<String> varargs, Control myControl)
    {
        super(varargs, myControl);
        super.numberOfArgs= NUMARGS;
        index = myControl.getShape();
        System.out.println("Shape index = " + index);
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
