package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Sum extends Command {
  private double a, b, sum;
  private int number=2;

  public Sum(){
    super();
    super.numberOfArgs=number;
  }

  public Sum(LinkedList<String> varargs, Control control){
    super(varargs, control);
   //myControl;
    a = Double.parseDouble(varargs.get(0));
    b = Double.parseDouble(varargs.get(1));
    sum = a + b;
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(sum);
    return ret;
  }
}
