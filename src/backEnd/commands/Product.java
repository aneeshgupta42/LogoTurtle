package backEnd.commands;

import backEnd.commands.Command;

public class Product extends Command {
    double a, b, prod;


    public Product(String[] varargs) {
        super(varargs);
        a = Double.parseDouble(varargs[0]);
        b = Double.parseDouble(varargs[1]);
        prod = a * b;
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(prod);
        return ret;
    }
}
