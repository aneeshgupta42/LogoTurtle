package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class ListStart extends Command {
  private int number=0;
  public ListStart(){
    super();
    super.numberOfArgs=number;
  }

  public ListStart(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs=number;
  }
  @Override
  public int repeatCom() {
    return super.repeatCom();
  }
}
