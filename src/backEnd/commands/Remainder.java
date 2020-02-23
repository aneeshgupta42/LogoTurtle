package backEnd.commands;

import backEnd.commands.Command;

public class Remainder extends Command {
    double a, b, remainder;
    private int numArgs = 2;

    public Remainder(String[] varargs) {
        super(varargs);
        super.numberOfArgs = numArgs;
        a = Double.parseDouble(varargs[0]);
        b = Double.parseDouble(varargs[1]);
        remainder = a % b;
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(remainder);
        return ret;
    }
}
