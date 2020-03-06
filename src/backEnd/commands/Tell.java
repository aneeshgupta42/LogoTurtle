package backEnd.commands;

import controller.Control;
import java.util.LinkedList;
import java.util.List;

public class Tell extends Command {

  //private final int number;
  private double start;
  private double stop;
  private LinkedList<String> turtleID;

  public Tell(){
    super();
 //   super.numberOfArgs;
  }
  public Tell(List<String> varargs, Control control){
    super(varargs,control);
    System.out.println(varargs);
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}