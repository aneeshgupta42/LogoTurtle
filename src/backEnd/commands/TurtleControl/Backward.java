package backEnd.commands.TurtleControl;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;

public class Backward extends Command {

  private static final int NUMARGS = 1;
  private int distance;

  public Backward(){
    super();
    super.numberOfArgs=NUMARGS;
  }

  public Backward(List<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs= NUMARGS;
    distance = Integer.parseInt(varargs.get(0));
    double angle = control.getTurtleAngle();
    double newX = -(distance *(Math.sin(Math.toRadians((angle)))));
    double newY = +(distance *(Math.cos(Math.toRadians((angle)))));
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
