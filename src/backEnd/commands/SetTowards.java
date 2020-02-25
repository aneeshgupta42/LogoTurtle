package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class SetTowards extends Command {

  public SetTowards(LinkedList<String> varargs, Control control){
    super(varargs, control);
    System.out.println(varargs.get(0));
    System.out.print(varargs.get(1));

    // x and y are in reverse order
  }
}
