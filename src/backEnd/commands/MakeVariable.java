package backEnd.commands;

import Controller.Control;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MakeVariable extends Command {

  public Object getVariablesCreated;
  private String key;
  private String var;
  private static int number = 2;


  public MakeVariable(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs = number;
    key = varargs.get(0);
    var = varargs.get(1);
  }

  @Override
  public Map getVariablesCreated(){
    Map<String,String> variables = new HashMap<>();
    if(!variables.containsKey(key)){
      variables.put(key,var);
    }
    return variables;
  }

}
