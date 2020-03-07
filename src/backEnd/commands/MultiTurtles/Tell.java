package backEnd.commands.MultiTurtles;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;

public class Tell extends Command {


  public Tell(){
    super();
  }
  public Tell(List<String> varargs, Control control){
    super(varargs,control);
    System.out.println("TTHTHT");
  }

  @Override
  public String commandValueReturn() {
    return "hi";
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}