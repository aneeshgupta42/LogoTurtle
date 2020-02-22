package Controller;

public class Sine extends Command {

  public Sine(String[] varargs) {
    super(varargs);
  }

  @Override
  public String commandValueReturn() {
    return "10";
  }
}
