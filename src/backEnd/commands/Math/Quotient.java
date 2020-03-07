package backEnd.commands.Math;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

public class Quotient extends Command {
  private static final int NUMARGS = 2;
  private double quotient;

  public Quotient() {
    super();
    super.numberOfArgs = NUMARGS;
  }

  public Quotient(List<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

    double argOne = Double.parseDouble(varargs.get(1));
    double argTwo = Double.parseDouble(varargs.get(0));
    quotient = argOne / argTwo;

  }

  @Override
  public String commandValueReturn() {
    return Double.toString(quotient);
  }
  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
