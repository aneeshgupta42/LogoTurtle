package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

public class GetPenColor extends Command {
    private static final int NUMARGS = 0;
    private int index;

    public GetPenColor() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public GetPenColor(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        index = control.getPenColor();
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
