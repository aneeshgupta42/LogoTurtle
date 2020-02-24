package backEnd.commands;

import Controller.Control;

public class Quotient extends Command {
  private double a,b,quotient;
  private static final int number =2;

  public Quotient(String[] varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs= number;
    a = Double.parseDouble(varargs[0]);
    b = Double.parseDouble(varargs[1]);
    quotient = b/a;
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(quotient);
    return ret;
  }
}
