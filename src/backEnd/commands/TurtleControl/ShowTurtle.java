package backEnd.commands.TurtleControl;

import backEnd.commands.Command;
import controller.Control;
import java.util.List;

public class ShowTurtle extends Command {
    private static final int NUMARGS = 0;

    public ShowTurtle(){
      super();
      super.numberOfArgs= NUMARGS;
    }
    public ShowTurtle(List<String> varargs, Control control)
    {
      super(varargs, control);
      super.numberOfArgs= NUMARGS;
      control.setTurtleVisible(true);
    }

  @Override
  public String commandValueReturn() {
    String ret = Integer.toString(1);
    return ret;
  }

  @Override
  public double repeatCom() {
    return super.repeatCom();
  }
  }
