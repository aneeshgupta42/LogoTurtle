package backEnd.commands;

import Controller.Control;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MakeVariable extends Command {

  private String key;
  private String var;
  private static int number = 2;

public MakeVariable(){
  super();
  super.numberOfArgs = number;
}
  public MakeVariable(LinkedList<String> varargs, Control control){
    super(varargs, control);
    if(varargs.get(0).contains(":")){
      key =varargs.get(0);
      var = varargs.get(1);
    }
    else{
      var =varargs.get(0);
      key = varargs.get(1);
    }
  }

  @Override
  public Map getVariablesCreated(){
    Map<String,String> variables = new HashMap<>();
    if(!variables.containsKey(key)){
      variables.put(key,var);
    }
    return variables;
  }
  @Override
  public int repeatCom() {
    return 0;
  }
}
