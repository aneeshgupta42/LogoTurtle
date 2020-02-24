package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;

public class Sine extends Command {
  private double myAngle;
  private double myResult;
  private final int number =1;

  public Sine(String[] varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs=number;
    myAngle = Double.parseDouble(varargs[0]);
    myResult = Math.sin((Math.toRadians(myAngle)));
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(myResult);
    return ret;
  }
}
