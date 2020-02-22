package backEnd.commands;

import backEnd.commands.Command;

public class Sine extends Command {

  public Sine(String[] varargs) {
    super(varargs);
  }

  @Override
  public String commandValueReturn() {
    return "10";
  }
}
