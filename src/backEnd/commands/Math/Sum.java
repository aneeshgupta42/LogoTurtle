package backEnd.commands.Math;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

/**
 * @author: Aneesh Gupta, Turner Jordan
 *
 * The Sum class implements the SUM command functionality, following the Command superclass conventions.
 */

public class Sum extends Command {
  private static final int NUMARGS = 2;
  private double sum;

  public Sum() {
    super();
    super.numberOfArgs= NUMARGS;
  }

  public Sum(List<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;
    double argOne = Double.parseDouble(varargs.get(0));
    double argTwo = Double.parseDouble(varargs.get(1));
    sum = argTwo + argOne;
  }

  @Override
  public String commandValueReturn() {
    return Double.toString(sum);
  }
  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
