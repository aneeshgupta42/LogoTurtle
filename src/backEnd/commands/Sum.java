package backEnd.commands;

import backEnd.commands.Command;

public class Sum extends Command {
  double a, b, sum;

  public Sum(String[] varargs) {
    super(varargs);
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
