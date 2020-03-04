package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class For extends Command {

  private final int number = 2;
  private int start;
  private int stop;

  public For(){
    super();
    super.numberOfArgs =number;
  }
  public For(LinkedList<String> varags, Control control){
    super(varags,control);
    start = Integer.parseInt(varags.get(0));
    stop = Integer.parseInt(varags.get(1));
    System.out.println(start + " " + stop);
  }

  @Override
  public int repeatCom() {
    return (stop-start);
  }
}
