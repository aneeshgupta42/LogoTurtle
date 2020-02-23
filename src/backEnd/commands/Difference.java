package backEnd.commands;

public class Difference extends Command{
    private double a, b, diff;
    public Difference(String[] varargs) {
        super(varargs);
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
