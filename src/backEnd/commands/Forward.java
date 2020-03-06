package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.List;

public class Forward extends Command {
  private static final int NUMARGS = 1;
  private int distance;

  public Forward(){
    super();
    super.numberOfArgs=NUMARGS;
  }

  public Forward(List<String> varargs, Control control){
    super(varargs, control);
    distance = (int)(Double.parseDouble(varargs.get(0)));
    double angle = control.getTurtleAngle();
    double newX = (distance *(Math.sin(Math.toRadians((angle)))));
    double newY = -(distance *(Math.cos(Math.toRadians((angle)))));
    control.updateTurtle(newX, newY, 0, distance);
  }

  @Override
  public String commandValueReturn() {
    return Integer.toString(distance);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }

}
