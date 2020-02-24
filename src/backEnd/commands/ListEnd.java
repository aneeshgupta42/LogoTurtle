package backEnd.commands;

import Controller.Control;

public class ListEnd extends Command {
  private int number=0;
  public ListEnd(String[] varargs, Control control){
    super(varargs);
    super.numberOfArgs=number;
  }
}
