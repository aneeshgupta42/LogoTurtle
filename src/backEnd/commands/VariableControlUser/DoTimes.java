package backEnd.commands.VariableControlUser;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DoTimes extends Command {

  private final int number = 2;
  private Control c;
  private double repetition;
  private String key;
  private String val;

  public DoTimes(){
    super();
    super.numberOfArgs=number;
  }
  public DoTimes(List<String> varargs, Control control){
    super(varargs, control);
    c = control;
    if(varargs.get(0).contains(":")){
      key =varargs.get(0);
      val = varargs.get(1);
    }
    else{
      val =varargs.get(0);
      key = varargs.get(1);
    }
  }

  public Map getVariablesCreated(){
    Map<String,String> map = new TreeMap();
    if(!key.equals(val)) {
      if (!map.containsKey(key)) {
        map.put(key, val);
      }}
    return map;
  }

  @Override
  public double repeatCom() {
    repetition = Double.parseDouble(val);
    return repetition;
  }


}
