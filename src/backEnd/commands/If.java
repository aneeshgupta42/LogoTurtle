package backEnd.commands;

import controller.Control;
import java.util.List;

public class If extends Command {

  private final int number =1;
  private double check;
  public If(){
    super();
    super.numberOfArgs=number;
  }

  public If(List<String> varargs, Control control){
    super(varargs, control);
    check = Double.parseDouble(varargs.get(0));
  }

  @Override
  public double runnable() {
    return check;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
