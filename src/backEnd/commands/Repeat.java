package backEnd.commands;

import java.util.LinkedList;

public class Repeat extends Command {

  private static final int number =  2;
  public Repeat(LinkedList<String> varargs) {
    super(varargs);
  }
  @Override
  public int getNumberOfArgs(){
    return number;
  }
}
