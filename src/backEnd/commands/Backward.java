package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Backward extends Command {

  private static final int NUMARGS = 1;

  public Backward(){
    super();
    super.numberOfArgs=NUMARGS;
  }

  public Backward(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs= NUMARGS;
    int distance = Integer.parseInt(varargs.get(0));
    double angle = control.getTurtleAngle();
//    System.out.println("Angle: " + angle);
    double newX = -(distance *(Math.sin(Math.toRadians((angle)))));
    double newY = +(distance *(Math.cos(Math.toRadians((angle)))));
    System.out.println("d " + distance);
    control.updateTurtle(newX, newY, 0, distance);
  }
  @Override
  public int repeatCom() {
    return super.repeatCom();
  }

}
