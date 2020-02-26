package Controller;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StoreLists {
  private Deque<String> lines;
  private Map<String,String> function;
  private Deque<String> args;

  public StoreLists(){
    function = new HashMap<>();
    lines = new LinkedList<>();
    args = new LinkedList<>();
  }

  public void store(String line){
    lines.push(line);
  }
  public void storeArg(LinkedList<String> arg) {

    for (String s:arg) {
      args.add(s);
    }
  }

  public void storeFunction(String functionName, String line){
    String func ="";
    for(String lines :line.split("\n")){
    for (String word : lines.split(" ")) {
      if (!word.equals("to") && !word.equals(functionName) && !word.equals("[") && !word.equals("]")) {
            func = func + " " + word;
          }
    }}
    function.put(functionName,func);
  }

  public Deque getCommands(){
    return lines;
  }
  public Deque getArguments(){
    return args;
  }
  public Map getFunction(){
    System.out.println(function);
    return function;}


}
