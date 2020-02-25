package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Sum extends Command {
  private static final int NUMARGS = 2;
  private double sum;

  public Sum() {
    super();
    super.numberOfArgs= NUMARGS;
  }

  public Sum(LinkedList<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

    double argTwo = Double.parseDouble(varargs.get(0));
    double argOne = Double.parseDouble(varargs.get(1));
    sum = argTwo + argOne;

    System.out.println("Sum is " + sum);
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(sum);
    return ret;
  }
}
