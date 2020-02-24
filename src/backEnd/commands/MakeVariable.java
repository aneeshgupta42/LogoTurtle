package backEnd.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MakeVariable extends Command {

  public Object getVariablesCreated;
  private String key;
  private String var;
  private static int number = 2;


  public MakeVariable(String[] varargs) {
    super(varargs);
    super.numberOfArgs = number;
    key = varargs[0];
    var = varargs[1];
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
