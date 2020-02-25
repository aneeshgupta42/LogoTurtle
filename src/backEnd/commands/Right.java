package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Right extends Command {
  public Right(LinkedList<String> varargs, Control myControl){
    super(varargs, myControl);
    double angle = Double.parseDouble(varargs.get(0));
    myControl.updateTurtle(0,0, angle);
  }

}
