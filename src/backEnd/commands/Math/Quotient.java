package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Quotient extends Command {
  private double quotient;
  private static final int NUMARGS = 2;

  public Quotient() {
    super();
    super.numberOfArgs = NUMARGS;
  }

  public Quotient(LinkedList<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

    double a = Double.parseDouble(varargs.get(0));
    double b = Double.parseDouble(varargs.get(1));
    quotient = b / a;

    System.out.println("Quotient is " + quotient);
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(quotient);
    return ret;
  }
}
