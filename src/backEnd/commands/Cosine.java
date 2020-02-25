package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class Cosine extends Command{
    private double myResult;
    private final int NUMARGS = 1;

    public Cosine(){
        super();
        super.numberOfArgs= NUMARGS;
    }

    public Cosine(LinkedList<String> varargs, Control control){
        super(varargs, control);

        double myAngle = Double.parseDouble(varargs.get(0));
        myResult = Math.cos((Math.toRadians(myAngle)));
        System.out.println("Angle"+myAngle);
        System.out.println("Cos"+myResult);
    }

    @Override
    public String commandValueReturn() {
        String ret = Double.toString(myResult);
        return ret;
    }
}
