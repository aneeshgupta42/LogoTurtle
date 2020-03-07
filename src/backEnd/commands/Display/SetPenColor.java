package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

public class SetPenColor extends Command{
    private static final int NUMARGS = 1;
    private int index;

    public SetPenColor() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public SetPenColor(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        index = Integer.parseInt(varargs.get(0));
        //control.setPenColor();
    }
}
