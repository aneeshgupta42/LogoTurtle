package backEnd.commands.VariableControlUser;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MakeUserInstruction extends Command {

  private final int number = 2;
  private String key;
  private String var;
  private final String WHITESPACE = " ";

  public MakeUserInstruction(){
    super();
    super.numberOfArgs=number;
  }

  public MakeUserInstruction(List<String> varargs, Control control){
    super(varargs,control);
    if(varargs.get(0).contains(":")){
      key =varargs.get(0);
      var = varargs.get(1);
    }
    else if(varargs.get(1).contains(":")){
      var =varargs.get(0);
      key = varargs.get(1);
    }
  }

  @Override
  public boolean storeCommands() {
    return true;
  }

  @Override
  public Map<String, String> getVariablesCreated() {
    Map<String,String> map = new TreeMap();
    if(!key.equals(var) && !key.equals(WHITESPACE)) {
      if (!map.containsKey(key)) {
        map.put(key, var);
      }}
    return map;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
