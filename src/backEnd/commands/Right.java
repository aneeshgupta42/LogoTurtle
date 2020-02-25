package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Right extends Command {
  private static final int NUMARGS = 1;

  public Right(){
    super();
    super.numberOfArgs= NUMARGS;
  }

  public Right(LinkedList<String> varargs, Control myControl){
    super(varargs, myControl);
    double angle = Double.parseDouble(varargs.get(0));
    myControl.updateTurtle(0,0, angle, 0);
    System.out.println("rt " + angle);
  }
  @Override
  public int repeatCom() {
    return 0;
  }

}
