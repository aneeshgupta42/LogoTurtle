package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;

import java.util.LinkedList;

public class Sine extends Command {
  private double myResult;
  private final int NUMARGS = 1;

  public Sine(){
    super();
    super.numberOfArgs= NUMARGS;
  }

  public Sine(LinkedList<String> varargs, Control control){
    super(varargs, control);

    double myAngle = Double.parseDouble(varargs.get(0));
    myResult = Math.sin((Math.toRadians(myAngle)));
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(myResult);
    return ret;
  }
}
