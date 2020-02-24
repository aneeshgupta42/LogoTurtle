package backEnd.commands;

import Controller.Control;

public class Repeat extends Command {

  private int repetition;
  private Control c;
  private static final int number =  2;

  public Repeat(String[] varargs, Control control) {
    super(varargs);
    super.setControl(control);
    c = control;
    repetition = Integer.parseInt(varargs[0]);
    super.numberOfArgs = number;
  }

  @Override
  public int repeatCom(){
  //  for(int i=0;i<repetition;i++){
  //   c.userInputCom();
 // }
    return repetition;
  }

}
