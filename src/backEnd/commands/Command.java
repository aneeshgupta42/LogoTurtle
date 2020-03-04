package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;
import java.util.Map;

public abstract class Command {

  private String language;
  private String myCommand;
  private String commandReturn;
  private Map<String, String> c;
  protected int numberOfArgs;
  private Control myControl;
  protected Boolean store = false;
  protected Boolean repeat = false;
  private int b;

  public Command(){
  }

  public Command(LinkedList<String> varargs, Control control) {
    myControl = control;
  }
  public void setControl(Control control){
      myControl = control;
  }
  public String commandValueReturn(){
    return commandReturn;
  }
  public Map<String, String> getVariablesCreated(){return c;}
  public int getNumberOfArgs(){return numberOfArgs;}
  public int repeatCom(){return b;}
  public boolean runnable(){return repeat;}
  public boolean storeCommands(){ return store; }


}
