package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Tell extends Command {

  private final int number = 3;
  private double start;
  private double stop;

  public Tell(){
    super();
    super.numberOfArgs =number;
  }
  public Tell(LinkedList<String> varags, Control control){
    super(varags,control);
    start = Double.parseDouble(varags.get(0));
    stop = Double.parseDouble(varags.get(1));
  }

  @Override
  public double repeatCom() {
    return (stop-start);
  }
}