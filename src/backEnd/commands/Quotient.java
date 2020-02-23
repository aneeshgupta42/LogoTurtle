package backEnd.commands;

public class Quotient extends Command {
  private double a,b,quotient;
  public Quotient(String[] varargs) {
    super(varargs);
    a = Double.parseDouble(varargs[0]);
    b = Double.parseDouble(varargs[1]);
    quotient = b/a;
  }

  @Override
  public String commandValueReturn() {
    String ret = Double.toString(quotient);
    return ret;
  }
}
