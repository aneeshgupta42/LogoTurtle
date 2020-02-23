package backEnd.commands;

import java.util.LinkedList;

public class Forward extends Command {
  int initX, initY;
  double angle;
  public Forward(String... varargs){
    super(varargs);
    System.out.println(varargs[0]);
    System.out.println(varargs[2]);
    
  }

}
