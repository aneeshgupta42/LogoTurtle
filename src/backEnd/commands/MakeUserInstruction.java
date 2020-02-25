package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class MakeUserInstruction extends Command {

  public MakeUserInstruction(){
    super();
  }

  public MakeUserInstruction(LinkedList<String> varags, Control control){
    super(varags,control);
  }

  @Override
  public int repeatCom() {
    return 0;
  }
}
