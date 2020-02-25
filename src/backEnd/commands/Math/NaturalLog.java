package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class NaturalLog extends Command {
    private static final int NUMARGS = 1;
    private double log;

    public NaturalLog() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public NaturalLog(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        log = Math.log(argOne);

        System.out.println("Log is: " + log);
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(log);
    }
    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
