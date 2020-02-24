package backEnd.commands;
import backEnd.commands.Command;

public class Difference extends Command {
    double a, b, diff;
    private int numArgs = 2;

    public Difference(String[] varargs) {
        super(varargs);
        super.numberOfArgs = numArgs;
        a = Double.parseDouble(varargs[0]);
        b = Double.parseDouble(varargs[1]);
        diff = a - b;
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(diff);
        return ret;
    }
}
