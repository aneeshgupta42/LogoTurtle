package backEnd.commands;

import backEnd.commands.Command;

public class Sum extends Command {
  private double a, b, sum;
  private int number=2;

  public Sum(String[] varargs) {
    super(varargs);
    super.numberOfArgs=number;
    a = Double.parseDouble(varargs[0]);
    b = Double.parseDouble(varargs[1]);
    sum = a + b;
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(sum);
    return ret;
  }
}
