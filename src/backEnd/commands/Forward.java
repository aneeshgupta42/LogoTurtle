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

//  protected void moveTurtle(int directionMultiplier, double distance) {
//    double angle = getAdjustedAngle(turtle.getDegree());
//    int xMultiplier = directionMultiplier * getXMultiplier(turtle.getDegree());
//    int yMultiplier = directionMultiplier * getYMultiplier(turtle.getDegree());
//
//    double angleToRadians = degreesToRadians(angle);
//    double rightAngle = degreesToRadians(FACING_RIGHT);
//
//    double newX = turtle.getX() + xMultiplier * (distance * Math.sin(angleToRadians));
//    double newY = turtle.getY() + yMultiplier * (distance * Math.sin(rightAngle - angleToRadians));
//
////    turtle.setX(newX);
////    turtle.setY(newY);
//    turtle.setCoordinate(newX, newY);
//  }
//
//  protected int getXMultiplier(double angle) {
//    if (angle >= 0 && angle < FACING_DOWN) {
//      return 1;
//    } else {
//      return -1;
//    }
//  }
//
//  protected int getYMultiplier(double angle) {
//    if (angle >= 90 && angle < 270) {
//      return -1;
//    } else {
//      return 1;
//    }
//  }




}
