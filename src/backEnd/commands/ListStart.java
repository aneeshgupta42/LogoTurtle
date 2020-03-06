package backEnd.commands;

import controller.Control;
import java.util.List;

public class ListStart extends Command {
  private int number=0;
  public ListStart(){
    super();
    super.numberOfArgs=number;
  }

  public ListStart(List<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs=number;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }

  @Override
  public boolean storeCommands() {
    return super.storeCommands();
  }
}
