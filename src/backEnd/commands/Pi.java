package backEnd.commands;

public class Pi extends Command {

  private int number =0;
  public Pi(String[] varargs) {
    super(varargs);
    super.numberOfArgs = number;
  }

  @Override
  public String commandValueReturn(){

    return "3.14";
  }

}

