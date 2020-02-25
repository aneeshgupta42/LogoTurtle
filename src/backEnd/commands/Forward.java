package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Forward extends Command {
  private static final int NUMARGS = 1;
  private int distance;

  public Forward(){
    super();
    super.numberOfArgs=NUMARGS;
  }

  public Forward(LinkedList<String> varargs, Control control){
    super(varargs, control);
    System.out.println("Reached");
    int i=0;
    i++;
    System.out.println(i);
    distance = (int)(Double.parseDouble(varargs.get(0)));
    double angle = control.getTurtleAngle();
//    System.out.println("Angle: " + angle);
    System.out.println("");
    double newX = (distance *(Math.sin(Math.toRadians((angle)))));
    double newY = -(distance *(Math.cos(Math.toRadians((angle)))));
    System.out.println("d " + distance + newX + newY);
    control.updateTurtle(newX, newY, 0, distance);
    System.out.println("fd " + newX +" "+ newY +" "+  distance);
  }

  @Override
  public String commandValueReturn() {
    return Integer.toString(distance);
  }

  @Override
  public int repeatCom() {
    return 0;
  }

}
