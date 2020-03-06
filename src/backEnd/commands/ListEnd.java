package backEnd.commands;

import controller.Control;
import java.util.List;

public class ListEnd extends Command {
  private int number=0;
  public ListEnd(){
    super();
    super.numberOfArgs=number;
  }
  public ListEnd(List<String> varargs, Control control){
    super(varargs, control);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
