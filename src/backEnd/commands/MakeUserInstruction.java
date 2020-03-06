package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.List;

public class MakeUserInstruction extends Command {

  private final int number = 0;
  public MakeUserInstruction(){
    super();
    super.numberOfArgs=number;
  }

  public MakeUserInstruction(List<String> varargs, Control control){
    super(varargs,control);

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
