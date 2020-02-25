package Controller;

import backEnd.commands.Command;
import java.util.Deque;
import java.util.LinkedList;

public class StoreLists {
  private Deque<String> lines;
  private Deque<Command> coms;
  private Deque<String> args;

  public StoreLists(){
    coms = new LinkedList<>();
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

  public Deque print(){
    return lines;
  }
  public Deque print2(){
    return args;
  }

}
