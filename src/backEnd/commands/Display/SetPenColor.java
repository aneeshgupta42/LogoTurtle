package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

public class SetPenColor extends Command{
    private static final int NUMARGS = 1;
    private int index, userIndex;

    public SetPenColor() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public SetPenColor(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        userIndex = Integer.parseInt(varargs.get(0));
        index = userIndex - 1;
        control.setPenColor(index);
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
