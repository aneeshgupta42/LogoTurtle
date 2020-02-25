package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Left extends Command {

  public Left(LinkedList<String> varargs, Control myControl){
    super(varargs, myControl);
    double angle = Double.parseDouble(varargs.get(0));
    myControl.updateTurtle(0,0, -angle);
  }
}
