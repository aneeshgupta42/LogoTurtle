package backEnd.commands;

public class Pi extends Command {

  public Pi(String[] varargs) {
    super(varargs);
  }

  @Override
  public String commandValueReturn(){
    return "3.14";
  }

}
