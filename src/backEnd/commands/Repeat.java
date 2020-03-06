package backEnd.commands;

import controller.Control;
import java.util.List;

public class Repeat extends Command {

  private double repetition;
  private Control c;
  private static final int number =  1;

  public Repeat(){
    super();
    super.numberOfArgs = number;
  }
  public Repeat(List<String> varargs, Control control){
    super(varargs, control);
    c = control;
    repetition = Double.parseDouble(varargs.get(0));
  }

  @Override
  public double repeatCom(){
    return repetition;
  }
}
