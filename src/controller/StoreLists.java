package controller;

import java.util.Map;
import java.util.TreeMap;

public class StoreLists {
  private Map<String,String> function;

  public StoreLists(){
    function = new TreeMap<>();
  }

  public void storeFunction(String command, String line){
    String functionName = command;
    String store = line;
    function.put(functionName,store);
  }

  public Map getFunction(){return function;}

}
