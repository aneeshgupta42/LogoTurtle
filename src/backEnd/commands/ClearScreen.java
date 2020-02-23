package backEnd.commands;

public class ClearScreen extends Command {

  private static final int number = 1;

  public ClearScreen(String[] varargs) {
    super(varargs);
    super.numberOfArgs=number;
  }

  @Override
  public int getNumberOfArgs(){
    return number;
  }
}
