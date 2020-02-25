package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class DoTimes extends Command {

  public DoTimes(LinkedList<String> varargs, Control control){
    super(varargs, control);
  }
}
