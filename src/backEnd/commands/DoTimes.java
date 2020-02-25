package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.LinkedList;

public class DoTimes extends Command {

  private final int number = 1;
  private Control c;
  private int repetition;

  public DoTimes(){
    super();
    super.numberOfArgs=number;
  }
  public DoTimes(LinkedList<String> varargs, Control control){
    super(varargs, control);
    c = control;
    repetition = Integer.parseInt(varargs.get(0));
  }

  @Override
  public int repeatCom() {
    return repetition;
  }
}
