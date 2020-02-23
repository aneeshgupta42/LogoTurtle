package backEnd.commands;

import backEnd.commands.Command;

public class Sine extends Command {
  private double myAngle;
  private double myResult;

  public Sine(String[] varargs)
  {
    super(varargs);
    myAngle = Double.parseDouble(varargs[0]);
    myResult = Math.sin((Math.toRadians(myAngle)));
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(myResult);
    return ret;
  }
}
