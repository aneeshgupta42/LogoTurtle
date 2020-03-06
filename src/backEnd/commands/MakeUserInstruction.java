package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class MakeUserInstruction extends Command {

  private final int number = 0;
  public MakeUserInstruction(){
    super();
    super.numberOfArgs=number;
  }

  public MakeUserInstruction(LinkedList<String> varags, Control control){
    super(varags,control);

  }

  @Override
  public boolean storeCommands() {
    return true;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
