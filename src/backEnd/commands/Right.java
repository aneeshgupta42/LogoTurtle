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
    super.numberOfArgs= NUMARGS;
    double angle = Double.parseDouble(varargs.get(0));
    System.out.println("hi");
    myControl.updateTurtle(0,0, angle, 0);
  }
  @Override
  public int repeatCom() {
    return 0;
  }

}
