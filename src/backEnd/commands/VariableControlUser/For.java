package backEnd.commands.VariableControlUser;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class For extends Command {

  private final int number = 4;
  private double start;
  private double stop;
  private String var;
  private double increment;
  private String key;
  private String val;

  public For(){
    super();
    super.numberOfArgs =number;
  }
  public For(List<String> varargs, Control control){
    super(varargs,control);
    increment = Double.parseDouble(varargs.get(0));
    stop = Double.parseDouble(varargs.get(1));
    start = Double.parseDouble(varargs.get(2));
    var = (varargs.get(3));

    key = var;
    val = Double.toString((stop-start));
}

  public Map getVariablesCreated(){
    Map<String,String> map = new TreeMap();
    if(!key.equals(val) && Double.parseDouble(val)>0) {
      if (!map.containsKey(key)) {
        map.put(key, val);
      }}
    return map;
  }

  @Override
  public double repeatCom() {
    return ((stop-start)/increment);
  }
}
