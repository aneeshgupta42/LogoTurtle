package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Backward extends Command {

  private static final int number = 1;

  public Backward(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs=number;
  }

}
