package backEnd.commands;
import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Difference extends Command {
    double a, b, diff;
    private int numArgs = 2;

    public Difference(LinkedList<String> varargs, Control control){
        super(varargs, control);
        super.numberOfArgs = numArgs;
        a = Double.parseDouble(varargs.get(0));
        b = Double.parseDouble(varargs.get(1));
        diff = a - b;
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(diff);
        return ret;
    }
}
