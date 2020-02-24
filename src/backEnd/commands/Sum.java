package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;

public class Sum extends Command {
  private double a, b, sum;
  private int number=2;

  public Sum(String[] varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs=number;
   //myControl;
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
