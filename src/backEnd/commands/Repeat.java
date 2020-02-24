package backEnd.commands;

import Controller.Control;
import backEnd.commands.Command;

public class Repeat extends Command {

  private int repetition;
  private static final int number =  2;

  public Repeat(String[] varargs) {
    super(varargs);
    repetition = Integer.parseInt(varargs[0]);
    super.numberOfArgs = number;
  }

  @Override
  public void setControl(Control control) {
    super.setControl(control);
    for(int i=0;i<repetition;i++){
      control.coordinateCommands();
      System.out.println(i);
    }
  }
}
