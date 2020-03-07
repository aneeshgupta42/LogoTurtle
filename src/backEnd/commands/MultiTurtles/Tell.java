package backEnd.commands.MultiTurtles;

import backEnd.commands.Command;
import controller.Control;

import java.util.ArrayList;
import java.util.List;

public class Tell extends Command {
  private final int NUMARGS = -1;
  private String lastID;
  public Tell(){
    super();
    super.numberOfArgs = NUMARGS;
  }
  public Tell(List<String> varargs, Control control){
    super(varargs,control);
    super.numberOfArgs = NUMARGS;
    lastID = varargs.get(0);
    ArrayList<Double> ids = new ArrayList<>();
    for (int i = 0; i<varargs.size(); i++){
      System.out.println(varargs.get(i));
      ids.add(i, Double.parseDouble(varargs.get(i)));
    }
    System.out.println("SIZES" + varargs.size()+ids.size());
    control.updateTurtleActive(ids);
    System.out.println("TTHTHT");
  }

  @Override
  public String commandValueReturn() {
    return lastID;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}