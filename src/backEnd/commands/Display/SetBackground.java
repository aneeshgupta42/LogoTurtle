package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

public class SetBackground extends Command{
    private static final int NUMARGS = 1;
    private int userIndex, index;

    public SetBackground() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public SetBackground(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        userIndex = Integer.parseInt(varargs.get(0));
        index = userIndex - 1;
        control.setBackgroundColor(index);
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
