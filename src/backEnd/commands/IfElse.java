package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class IfElse extends Command{

  private final int number =1;
  private int check;
  public IfElse(){
    super();
    super.numberOfArgs=number;
  }
  public IfElse(LinkedList<String> varags, Control control){
    super(varags,control);
    check = Integer.parseInt(varags.get(0));
  }

  @Override
  public boolean runnable() {
    return check!=0;
  }

  @Override
  public int repeatCom() {
    return 0;
  }
}
