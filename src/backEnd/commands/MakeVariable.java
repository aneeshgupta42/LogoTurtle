package backEnd.commands;

import backEnd.commands.Command;

public class MakeVariable extends Command {

  public MakeVariable(String[] varargs) {
    super(varargs);
    System.out.println(varargs[0]);
    System.out.println(varargs[2]);
  }
}
