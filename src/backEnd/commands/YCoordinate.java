package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class YCoordinate extends Command {
    private static final int NUMARGS = 0;
    private double returnYCord;

    public YCoordinate() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public YCoordinate(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        returnYCord = control.getTurtleRelativeYPos();
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(returnYCord);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
