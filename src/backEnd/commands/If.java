package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class If extends Command {

  private final int number =1;
  private int check;
  public If(){
    super();
    super.numberOfArgs=number;
  }

  public If(LinkedList<String> varargs, Control control){
    super(varargs, control);
    check = Integer.parseInt(varargs.get(0));
  }

  @Override
  public boolean runnable() {
    return check != 0;
  }

  @Override
  public int repeatCom() {
    return 0;
  }
}
