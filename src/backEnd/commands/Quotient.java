package backEnd.commands;

import java.util.LinkedList;

public class Quotient extends Command {
  private double a,b,quotient;
  private static final int number =2;
  public Quotient(LinkedList<String> varargs) {
    super(varargs);
    super.numberOfArgs=number;
    a = Double.parseDouble(varargs.get(0));
    b = Double.parseDouble(varargs.get(1));
    quotient = b/a;
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(quotient);
    return ret;
  }
}
