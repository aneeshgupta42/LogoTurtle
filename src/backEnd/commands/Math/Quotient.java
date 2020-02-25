package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Quotient extends Command {
  private static final int NUMARGS = 2;
  private double quotient;

  public Quotient() {
    super();
    super.numberOfArgs = NUMARGS;
  }

  public Quotient(LinkedList<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

    double argTwo = Double.parseDouble(varargs.get(0));
    double argOne = Double.parseDouble(varargs.get(1));
    quotient = argOne / argTwo;

    System.out.println("Quotient is " + quotient);
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(quotient);
    return ret;
  }
}
