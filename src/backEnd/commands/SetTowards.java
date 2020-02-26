package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class SetTowards extends Command {
  private double angle_moved;
  private static final int NUMARGS = 2;
  public SetTowards(){
    super();
    super.numberOfArgs=NUMARGS;
  }

  public SetTowards(LinkedList<String> varargs, Control control){
    super(varargs, control);
    System.out.println("Reached");
    double currAngle = control.getTurtleAngle();
    double X = (Double.parseDouble(varargs.get(0)));
    double Y = (Double.parseDouble(varargs.get(1)));
    System.out.println("towards arg" + varargs.get(0) + varargs.get(1));
    double initX = control.getTurtleRelativeXPos();
    double initY = control.getTurtleRelativeYPos();
    double delX = X - initX; double delY = Y - initY;
    System.out.println("init" + initX + initY);
    System.out.println("towards del: " + delX + delY);
    double theta = 90 - Math.toDegrees(Math.atan(delY/delX));
    System.out.println("theta: " + theta);

    control.updateTurtle(0, 0, -currAngle + theta, 0);
    angle_moved = theta -currAngle;
  }

  @Override
  public String commandValueReturn() {
    return Double.toString(angle_moved);
  }

  @Override
  public int repeatCom() {
    return 0;
  }
}
