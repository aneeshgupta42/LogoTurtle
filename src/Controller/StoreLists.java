package Controller;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StoreLists {
  private Deque<String> lines;
  private Map<String,String> function;
  private Deque<String> args;
  private Deque<String> words;

  public StoreLists(){
    function = new HashMap<>();
    args = new LinkedList<>();
    lines = new LinkedList<>();
    words = new LinkedList<>();
  }

  public void storeForArg(String line){
    for (String word:line.split(" ")) {
      if(!line.equals("[") && !line.equals("]") && !line.equals("For"))
        words.add(word);
    }
  }
  public void store(String line){
    if(!line.equals("[") && !line.equals("]") )
      lines.add(line);
  }

  public void storeArg(LinkedList<String> arg) {
    for (String s:arg) {
      args.push(s);
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

  public Deque<String> getForArgs() {
    return words;
  }
  public Deque<String> getCommands(){
    return lines;
  }
  public Deque<String> getArguments(){
    return args;
  }

  public Map<String, String> getFunction(){
    return function;}
    public int getLength(){
      if(lines.size()>0) return lines.size();
      else return 1;
    }
}
