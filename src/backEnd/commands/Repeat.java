package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Repeat extends Command {

  private int repetition;
  private Control c;
  private static final int number =  2;

  public Repeat(LinkedList<String> varargs, Control control){
    super(varargs, control);
    super.setControl(control);
    c = control;
    repetition = Integer.parseInt(varargs.get(0));
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
