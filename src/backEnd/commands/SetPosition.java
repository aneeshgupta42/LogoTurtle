package backEnd.commands;

import Controller.Control;

import java.util.LinkedList;

public class SetPosition extends Command {
// GOTO
// GOTO
private static final int NUMARGS = 2;
    private double distance;

    public SetPosition(){
        super();
        super.numberOfArgs=NUMARGS;
    }

    public SetPosition(LinkedList<String> varargs, Control control){
        super(varargs, control);
        System.out.println("Reached");
        double X = (Double.parseDouble(varargs.get(0)));
        double Y = (Double.parseDouble(varargs.get(1)));
        double initX = control.getTurtleRelativeXPos();
        double initY = control.getTurtleRelativeYPos();
        double newX = X - initX;
        double newY = initY - Y;
        distance = Math.sqrt(newX*newX + newY*newY);
        System.out.println("d " + distance + newX + newY);
        control.updateTurtle(newX, newY, 0, (int)distance);
        System.out.println("fd " + newX +" "+ newY +" "+  (int)distance);
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(distance);
    }

    @Override
    public int repeatCom() {
        return 0;
    }
}
