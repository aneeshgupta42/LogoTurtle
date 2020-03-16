package controller;

import java.util.Map;
import java.util.TreeMap;
/*
@author Libba Lawrence
 */
public class StoreFunctions {
  private Map<String,String> function;

  /*
  Initializes the map to store functions in
   */
  public StoreFunctions(){
    function = new TreeMap<>();
  }

  /*
  Creates the key and input for the map for each function
  @param command, line
   */
  public void storeFunction(String command, String line){
    String functionName = command;
    String store = line;
    function.put(functionName,store);
  }

  /*
  Returns the lists of functions
  @return function
   */
  public Map getFunction(){return function;}

}
