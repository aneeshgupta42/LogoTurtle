package backEnd.commands;

import java.util.LinkedList;

public class ClearScreen extends Command {

  private static final int number = 1;

  public ClearScreen(LinkedList<String> varargs) {
    super(varargs);
    super.numberOfArgs=number;
  }

  @Override
  public int getNumberOfArgs(){
    return number;
  }
}
