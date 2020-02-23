package backEnd.commands;


import java.util.LinkedList;

public class Sum extends Command {
  double a, b, sum;
  private int number = 2;

  public Sum(){
    super.numberOfArgs=number;
  }

  public Sum(LinkedList<String> varargs) {
    super(varargs);
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
