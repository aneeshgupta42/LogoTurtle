package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

/**
 * @author: Aneesh Gupta, Turner Jordan
 *
 * The SetPenShape class implements the SetPenShape command functionality, following the Command superclass conventions.
 */
public class SetShape extends Command {
    private static final int NUMARGS = 1;
    private int index;

    public SetShape(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public SetShape(List<String> varargs, Control control)
    {
        super(varargs, control);
        super.numberOfArgs= NUMARGS;
        index = Integer.parseInt(varargs.get(0));
        control.setShape(index);
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
