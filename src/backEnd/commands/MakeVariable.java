package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
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
    Map<String,String> variables = new TreeMap<>();
    variables.put(key,var);
    return variables;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
}
