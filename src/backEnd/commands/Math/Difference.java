package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Difference extends Command {
    private static final int NUMARGS = 2;
    private double diff;

    public Difference() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Difference(LinkedList<String> varargs, Control control){
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));
        diff = argOne - argTwo;

        System.out.println("Difference is: " + diff);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(diff);
        return ret;
    }
    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
