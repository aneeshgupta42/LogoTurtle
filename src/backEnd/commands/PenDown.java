package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class PenDown extends Command {
  private final int NUMARGS = 0;
  public PenDown(){
    super();
    super.numberOfArgs = NUMARGS;
  }

  public PenDown(LinkedList<String> varargs, Control control)
  {
    super(varargs, control);
    control.setPenDown(true);
  }

  @Override
  public String commandValueReturn() {
    return Integer.toString(1);
  }

  @Override
  public int repeatCom() {
    return super.repeatCom();
  }
}
