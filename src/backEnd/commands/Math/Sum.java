package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class Sum extends Command {
  private final int NUMARGS = 2;
  private double sum;

  public Sum(){
    super();
    super.numberOfArgs= NUMARGS;
  }

  public Sum(LinkedList<String> varargs, Control control){
    super(varargs, control);

    super.numberOfArgs= NUMARGS;
   //myControl;
    double a = Double.parseDouble(varargs.get(0));
    double b = Double.parseDouble(varargs.get(1));
    sum = a + b;
    System.out.println("Sum is " + sum);
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(sum);
    return ret;
  }
}
