package backEnd.commands;

import Controller.Control;

public class Pi extends Command {

  private int number =0;
  public Pi(String[] varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = number;
  }

  @Override
  public String commandValueReturn(){
    return Double.toString(Math.PI);
  }

}

