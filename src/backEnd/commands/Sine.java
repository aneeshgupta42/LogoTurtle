package backEnd.commands;

import java.util.LinkedList;

public class Sine extends Command {
  private double myAngle;
  private double myResult;

  public Sine(LinkedList<String> varargs)
  {
    super(varargs);
    myAngle = Double.parseDouble(varargs.get(0));
    myResult = Math.sin((Math.toRadians(myAngle)));
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(myResult);
    return ret;
  }
}
