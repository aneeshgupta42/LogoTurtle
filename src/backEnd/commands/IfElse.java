package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class IfElse extends Command{

  private final int number =1;
  private double check;
  public IfElse(){
    super();
    super.numberOfArgs=number;
  }
  public IfElse(LinkedList<String> varags, Control control){
    super(varags,control);
    check = Double.parseDouble(varags.get(0));
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