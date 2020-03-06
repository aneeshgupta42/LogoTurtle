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
        double X = (Double.parseDouble(varargs.get(0)));
        double Y = (Double.parseDouble(varargs.get(1)));
        double initX = control.getTurtleRelativeXPos();
        double initY = control.getTurtleRelativeYPos();
        double newX = X - initX;
        double newY = initY - Y;
        distance = Math.sqrt(newX*newX + newY*newY);
        control.updateTurtle(newX, newY, 0, (int)distance);
    }

    @Override
    public String commandValueReturn() {
        return Double.toString(distance);
    }

    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
