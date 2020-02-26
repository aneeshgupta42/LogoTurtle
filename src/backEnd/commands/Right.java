package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Right extends Command {
  private static final int NUMARGS = 1;
  private double angle;

  public Right(){
    super();
    super.numberOfArgs= NUMARGS;
  }

  public Right(LinkedList<String> varargs, Control myControl){
    super(varargs, myControl);
    super.numberOfArgs= NUMARGS;
    angle = Double.parseDouble(varargs.get(0));
    myControl.updateTurtle(0,0, angle, 0);
    System.out.println("rt done" + angle);
  }
  @Override
  public int repeatCom() {
    return 0;
  }

  @Override
  public String commandValueReturn() {
    return Double.toString(angle);
  }
}
