package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Product extends Command {
    double a, b, prod;
    private int numArgs = 2;


    public Product(LinkedList<String> varargs, Control control){
        super(varargs, control);
        super.numberOfArgs = numArgs;
        a = Double.parseDouble(varargs.get(0));
        b = Double.parseDouble(varargs.get(1));
        prod = a * b;
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(prod);
        return ret;
    }
}
