package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class PenDown extends Command {

  public PenDown(LinkedList<String> varargs, Control control){
    super(varargs, control);
  }
  @Override
  public int repeatCom() {
    return super.repeatCom();
  }
}
