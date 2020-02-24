package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;

public class SetTowards extends Command {

  public SetTowards(String[] varargs, Control control) {
    super(varargs, control);
    System.out.println(varargs[0]);
    System.out.print(varargs[1]);

    // x and y are in reverse order
  }
}
