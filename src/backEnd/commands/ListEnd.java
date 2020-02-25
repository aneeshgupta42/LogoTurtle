package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class ListEnd extends Command {
  private int number=0;
  public ListEnd(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs=number;
  }
}
