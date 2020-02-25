package backEnd.commands.Math;

import Controller.Control;
import backEnd.commands.Command;

import java.util.LinkedList;

public class Pi extends Command {

  private int number =0;
  public Pi(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.numberOfArgs = number;
  }

  @Override
  public String commandValueReturn(){
    return Double.toString(Math.PI);
  }

}

