package backEnd.commands;

import Controller.Control;

public class ClearScreen extends Command {

  private static final int number = 1;

  public ClearScreen(String[] varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs=number;
  }

  @Override
  public int getNumberOfArgs(){
    return number;
  }
}
