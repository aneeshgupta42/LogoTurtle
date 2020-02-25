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

    double argOne = Double.parseDouble(varargs.get(0));
    double argTwo = Double.parseDouble(varargs.get(1));
    quotient = argOne / argTwo;

    System.out.println("Quotient is " + quotient);
  }

  @Override
  public String commandValueReturn() {
    return Double.toString(quotient);
  }
  @Override
  public int repeatCom() {
    return super.repeatCom();
  }
}
