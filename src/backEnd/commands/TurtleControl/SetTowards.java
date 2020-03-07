package backEnd.commands.TurtleControl;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;

public class SetTowards extends Command {
  private double angle_moved;
  private static final int NUMARGS = 2;
  public SetTowards(){
    super();
    super.numberOfArgs=NUMARGS;
  }

  public SetTowards(List<String> varargs, Control control){
    super(varargs, control);
    double currAngle = control.getTurtleAngle();
    double X = (Double.parseDouble(varargs.get(0)));
    double Y = (Double.parseDouble(varargs.get(1)));
    double initX = control.getTurtleRelativeXPos();
    double initY = control.getTurtleRelativeYPos();
    double delX = X - initX; double delY = Y - initY;
    double theta = 90 - Math.toDegrees(Math.atan(delY/delX));
    if(delY == 0.0 && delX < 0){
      control.updateTurtle(0, 0, -currAngle - currAngle, 0);
    }
    else{
      control.updateTurtle(0, 0, -currAngle + theta, 0);
    }
    angle_moved = theta -currAngle;
  }

  @Override
  public String commandValueReturn() {
    return Double.toString(angle_moved);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
