package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.List;

public class For extends Command {

  private final int number = 2;
  private double start;
  private double stop;

  public For(){
    super();
    super.numberOfArgs =number;
  }
  public For(List<String> varargs, Control control){
    super(varargs,control);
    start = Double.parseDouble(varargs.get(0));
    stop = Double.parseDouble(varargs.get(1));
  }

  @Override
  public double repeatCom() {
    return (stop-start);
  }
}
