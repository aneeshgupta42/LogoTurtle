package backEnd.commands.TurtleControl;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

public class PenUp extends Command {
    private final int NUMARGS = 0;
    public PenUp(){
        super();
        super.numberOfArgs = NUMARGS;
    }

    public PenUp(List<String> varargs, Control control)
    {
        super(varargs, control);
        control.setPenDown(false);
    }

    @Override
    public String commandValueReturn() {
        return Integer.toString(0);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }

}
