package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
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
    System.out.println(check);
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
