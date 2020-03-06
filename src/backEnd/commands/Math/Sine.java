package backEnd.commands.Math;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

public class Sine extends Command {
  private static final int NUMARGS = 1;
  private double sinResult;

  public Sine() {
    super();
    super.numberOfArgs = NUMARGS;
  }

  public Sine(List<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

    double argOne = Double.parseDouble(varargs.get(0));
    sinResult = Math.sin((Math.toRadians(argOne)));

  }

  @Override
  public String commandValueReturn() {
    return Double.toString(sinResult);
  }
  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
