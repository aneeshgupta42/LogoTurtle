package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DoTimes extends Command {

  private final int number = 2;
  private Control c;
  private int repetition;
  private String key;
  private String val;

  public DoTimes(){
    super();
    super.numberOfArgs=number;
  }
  public DoTimes(LinkedList<String> varargs, Control control){
    super(varargs, control);
    c = control;
    val = varargs.get(1);
    key = varargs.get(0);
    System.out.println("dotime" + key + val);
  }

  public Map getVariablesCreated(){
    Map map = new HashMap();
    map.put(key,val);
    return map;
  }

  @Override
  public int repeatCom() {
    repetition = Integer.parseInt(val);
    return repetition;
  }


}
