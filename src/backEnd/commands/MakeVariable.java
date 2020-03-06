package backEnd.commands;

import controller.Control;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MakeVariable extends Command {

  private String key;
  private String var;
  private static int number = 2;

public MakeVariable(){
  super();
  super.numberOfArgs = number;
}
  public MakeVariable(List<String> varargs, Control control){
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
    Map<String,String> variables = new TreeMap<>();
    if(!key.equals(var)) {
      if (!variables.containsKey(key)) {
        variables.put(key, var);
      }
    }
    return variables;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
