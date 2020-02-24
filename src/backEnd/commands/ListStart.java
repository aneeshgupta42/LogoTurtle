package backEnd.commands;

import Controller.Control;

public class ListStart extends Command {
  private int number=0;
  public ListStart(String[] varargs, Control control){
    super(varargs);
    super.numberOfArgs=number;
  }
}
