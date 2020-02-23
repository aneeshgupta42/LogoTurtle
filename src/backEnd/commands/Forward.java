package backEnd.commands;

import Controller.Control;

public class Forward extends Command {
  private int initX, initY;
  private int distance;
  private final int number = 1;
  private double angle;
  private Control myControl;

  public Forward(String... varargs){
    super(varargs);
    super.numberOfArgs=number;
    distance = Integer.parseInt(varargs[0]);
    System.out.printf("distance");
    myControl = super.getMyControl();
//    initX = myControl.getTurtleCol();
//    initY = myControl.getTurtleRow();
//    angle = myControl.getTurtleAngle();
//    int newX = (int) (initX + distance*(Math.cos(angle)));
//    int newY = (int) (initY + distance*(Math.sin(angle)));
//    myControl.updateTurtle(newX, newY, angle);
  }

}
