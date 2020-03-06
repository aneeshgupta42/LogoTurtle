package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;
import java.util.List;

public class PenDown extends Command {
  private final int NUMARGS = 0;
  public PenDown(){
    super();
    super.numberOfArgs = NUMARGS;
  }

  public PenDown(List<String> varargs, Control control)
  {
    super(varargs, control);
    control.setPenDown(true);
  }

  @Override
  public String commandValueReturn() {
    return Integer.toString(1);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
