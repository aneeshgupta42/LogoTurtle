package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class ShowTurtle extends Command{

  public ShowTurtle(LinkedList<String> varargs, Control control){
    super(varargs, control);
  }
}
