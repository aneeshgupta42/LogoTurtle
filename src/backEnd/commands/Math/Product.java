package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Product extends Command {
    private static final int NUMARGS = 2;
    private double product;

    public Product() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Product(LinkedList<String> varargs, Control control){
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        double argTwo = Double.parseDouble(varargs.get(1));
        product = argTwo * argOne;

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(product);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
