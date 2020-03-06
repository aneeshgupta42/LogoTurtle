package backEnd.commands;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

public class Difference extends Command {
    private static final int NUMARGS = 2;
    private double diff;

    public Difference() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Difference(List<String> varargs, Control control){
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));
        diff = argOne - argTwo;

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(diff);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
