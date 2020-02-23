package backEnd.commands;

public class Backward extends Command {

  private static final int number = 1;

  public Backward(String[] varargs) {
    super(varargs);
    super.numberOfArgs=number;
  }

}
