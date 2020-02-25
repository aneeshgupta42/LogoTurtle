package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Cosine extends Command {
    private static final int NUMARGS = 1;
    private double cosResult;

    public Cosine() {
        super();
        super.numberOfArgs= NUMARGS;
    }

    public Cosine(LinkedList<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        cosResult = Math.cos((Math.toRadians(argOne)));

        System.out.println("Cosine is: " + cosResult);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(cosResult);
        return ret;
    }
    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
