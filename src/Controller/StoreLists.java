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
    args = new LinkedList<>();
    lines = new LinkedList<>();
  }

  public void store(String line, LinkedList<String> arg) {
    lines.push(line);

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
