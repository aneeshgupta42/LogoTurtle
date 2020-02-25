package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Pi extends Command {
  private static final int NUMARGS = 0;

  public Pi() {
    super();
    super.numberOfArgs = NUMARGS;
  }

  public Pi(LinkedList<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

    System.out.println("Pi is: " + Math.PI);
  }

  @Override
  public String commandValueReturn(){
    return Double.toString(Math.PI);
  }

  @Override
  public int repeatCom() {
    return super.repeatCom();
  }
}

