package backEnd.commands;

import java.util.LinkedList;

public class Forward extends Command {

  public Forward(LinkedList<String> varargs){
    super(varargs);

    System.out.println(varargs.get(0));
    System.out.println(varargs.get(2));
  }

}
