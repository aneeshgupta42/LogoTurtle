package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Sine extends Command {
  private double myAngle;
  private double myResult;
  private final int number =1;

  public Sine(){
    super();
    super.numberOfArgs=number;
  }
  public Sine(LinkedList<String> varargs, Control control){
    super(varargs, control);

    myAngle = Double.parseDouble(varargs.get(0));
    myResult = Math.sin((Math.toRadians(myAngle)));
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(myResult);
    return ret;
  }
}
