package backEnd.commands.Math;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

/**
 * @author: Turner Jordan
 *
 * The Pi class implements the PI command functionality, following the Command superclass conventions.
 * It uses the java Math package to compute the value.
 */

public class Pi extends Command {
  private static final int NUMARGS = 0;

  public Pi() {
    super();
    super.numberOfArgs = NUMARGS;
  }

  public Pi(List<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

  }

  @Override
  public String commandValueReturn(){
    return Double.toString(Math.PI);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}

