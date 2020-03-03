package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class If extends Command {

  private final int number =1;
  private double check;
  public If(){
    super();
    super.numberOfArgs=number;
  }

  public If(LinkedList<String> varargs, Control control){
    super(varargs, control);
    check = Double.parseDouble(varargs.get(0));
    System.out.println(check);
  }

  @Override
  public boolean runnable() {
    return check != 0.0;
  }

  @Override
  public int repeatCom() {
    return 0;
  }
}
