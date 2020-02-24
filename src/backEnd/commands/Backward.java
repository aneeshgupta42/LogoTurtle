package backEnd.commands;

import Controller.Control;

public class Backward extends Command {

  private static final int number = 1;

  public Backward(String[] varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs=number;
  }

}
