package backEnd.commands;

import java.util.LinkedList;

public class Pi extends Command {

  public Pi(LinkedList<String> varargs) {
    super(varargs);
  }

  @Override
  public String commandValueReturn(){
    return "3.14";
  }

}

