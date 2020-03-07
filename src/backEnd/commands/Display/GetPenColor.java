package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

public class GetPenColor extends Command {
    private static final int NUMARGS = 0;
    private int index, userIndex;

    public GetPenColor() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public GetPenColor(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        index = control.getPenColor();
        userIndex = index + 1;
    }

    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(userIndex);
        return ret;
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
