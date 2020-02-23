package backEnd.commands;

import java.util.LinkedList;

public class Backward extends Command {

  private static final int number = 1;

  public Backward(LinkedList varargs) {
    super(varargs);
    super.numberOfArgs=number;
  }

}
