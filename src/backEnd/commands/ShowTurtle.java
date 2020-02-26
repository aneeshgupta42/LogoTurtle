package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class ShowTurtle extends Command{
    private static final int NUMARGS = 0;

    public ShowTurtle(){
      super();
      super.numberOfArgs= NUMARGS;
    }
    public ShowTurtle(LinkedList<String> varargs, Control myControl)
    {
      super(varargs, myControl);
      super.numberOfArgs= NUMARGS;
      myControl.setTurtleVisible(true);
    }

  @Override
  public String commandValueReturn() {
    String ret = Integer.toString(1);
    return ret;
  }

  @Override
  public int repeatCom() {
    return super.repeatCom();
  }
  }
