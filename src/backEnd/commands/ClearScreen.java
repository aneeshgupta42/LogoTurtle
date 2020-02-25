package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class ClearScreen extends Command {

  private static final int number = 1;

  public ClearScreen(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs=number;
  }

  @Override
  public int getNumberOfArgs(){
    return number;
  }
}
