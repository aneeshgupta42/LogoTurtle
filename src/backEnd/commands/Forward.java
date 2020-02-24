package backEnd.commands;

import Controller.Control;

public class Forward extends Command {
  private static final int NUMARGS = 1;

  public Forward(String[] varargs, Control control){
    super(varargs);
    super.numberOfArgs= NUMARGS;
    int distance = Integer.parseInt(varargs[0]);
    double initX = control.getTurtleCol();
    double initY = control.getTurtleRow();
    double angle = control.getTurtleAngle();
    double newX = (distance *(Math.cos(Math.toRadians(angle%360))));
    double newY = (distance *(Math.sin(Math.toRadians(angle%360))));
    control.updateTurtle(newX, newY, 0);
  }

}
