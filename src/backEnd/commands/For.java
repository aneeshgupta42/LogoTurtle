package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class For extends Command {

  private final int number = 2;
  private double start;
  private double stop;

  public For(){
    super();
    super.numberOfArgs =number;
  }
  public For(LinkedList<String> varags, Control control){
    super(varags,control);
    start = Double.parseDouble(varags.get(0));
    stop = Double.parseDouble(varags.get(1));
    System.out.println(start + " " + stop);
  }

  @Override
  public double repeatCom() {
    return (stop-start);
  }
}
