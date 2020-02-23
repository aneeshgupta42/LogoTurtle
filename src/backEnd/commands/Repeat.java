package backEnd.commands;

import backEnd.commands.Command;

public class Repeat extends Command {

  private static final int number =  2;
  public Repeat(String[] varargs) {
    super(varargs);
  }
  @Override
  public int getNumberOfArgs(){
    return number;
  }
}
