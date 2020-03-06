package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.List;

public class Tell extends Command {

  private final int number = 3;
  private double start;
  private double stop;

  public Tell(){
    super();
    super.numberOfArgs =number;
  }
  public Tell(List<String> varargs, Control control){
    super(varargs,control);
    start = Double.parseDouble(varargs.get(0));
    stop = Double.parseDouble(varargs.get(1));
  }

  @Override
  public double repeatCom() {
    return (stop-start);
  }
}