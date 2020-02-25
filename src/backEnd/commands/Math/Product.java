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

        System.out.println("Product is: " + product);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(product);
        return ret;
    }
    @Override
    public int repeatCom() {
        return super.repeatCom();
    }
}
