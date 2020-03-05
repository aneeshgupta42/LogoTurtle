package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Tell extends Command {

  private final int number = 3;
  private int start;
  private int stop;

  public Tell(){
    super();
    super.numberOfArgs =number;
  }
  public Tell(LinkedList<String> varags, Control control){
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