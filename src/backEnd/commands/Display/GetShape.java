package backEnd.commands.Display;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

/**
 * @author: Aneesh Gupta, Turner Jordan
 *
 * The GetShape class implements the GetShape command functionality, following the Command superclass conventions.
 */
public class GetShape extends Command {
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
