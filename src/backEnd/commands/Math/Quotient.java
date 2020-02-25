package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;

import java.util.LinkedList;

public class Quotient extends Command {
  private double a,b,quotient;
  private static final int number =2;

  public Quotient(){
    super();
    super.numberOfArgs= number;
  }

  public Quotient(LinkedList<String> varargs, Control control){
    super(varargs, control);
    a = Double.parseDouble(varargs.get(0));
    b = Double.parseDouble(varargs.get(1));
    quotient = b/a;
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(quotient);
    return ret;
  }
}
