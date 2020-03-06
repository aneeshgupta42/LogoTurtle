package backEnd.commands.TurtleQueries;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;

public class IsPenDown extends Command {
    private final int NUMARGS = 0;
    private int return_value;

    public IsPenDown(){
        super();
        super.numberOfArgs = NUMARGS;
    }

    public IsPenDown(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        return_value = control.isPenDown() ? 1:0;
    }

    @Override
    public String commandValueReturn() {
        return Integer.toString(return_value);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }

}
