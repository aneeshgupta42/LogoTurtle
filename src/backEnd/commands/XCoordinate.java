package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.List;

public class XCoordinate extends Command {
    private static final int NUMARGS = 0;
    private double returnXCord;

    public XCoordinate() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public XCoordinate(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;
        returnXCord = control.getTurtleRelativeXPos();
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(returnXCord);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
