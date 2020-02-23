package backEnd.commands;

public class Quotient extends Command {
  private int a,b,quotient;
  public Quotient(String[] varargs) {
    super(varargs);
    a = Integer.parseInt(varargs[0]);
    b = Integer.parseInt(varargs[1]);
    quotient = b/a;
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(quotient);
    return ret;
  }
}
