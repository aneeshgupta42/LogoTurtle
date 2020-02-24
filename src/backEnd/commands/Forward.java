package backEnd.commands;

import Controller.Control;
import frontEnd.Turtle;

public class Forward extends Command {
  private double initX, initY;
  private int distance;
  private final int number = 1;
  private double angle;

  public Forward(String[] varargs, Control control){
    super(varargs);
    super.numberOfArgs=number;
    distance = Integer.parseInt(varargs[0]);
    Control myControl = control;
    initX = myControl.getTurtleCol();
    initY = myControl.getTurtleRow();
    angle = myControl.getTurtleAngle();
    int newX = (int) (distance*(Math.cos(angle)));
    int newY = (int) (distance*(Math.sin(angle)));
    myControl.updateTurtle(newX, newY, angle);
  }

}
