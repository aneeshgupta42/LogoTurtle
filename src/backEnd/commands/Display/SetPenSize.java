package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The SetPenSize class implements the SetPenSize command functionality, following the Command superclass conventions.
 */
public class SetPenSize extends Command{
    private static final int NUMARGS = 1;
    private double penWidth;

    public SetPenSize() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public SetPenSize(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        penWidth = Double.parseDouble(varargs.get(0));
        control.setPenWidth(penWidth);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(penWidth);
        return ret;
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
