package backEnd.commands;

import controller.Control;
import java.util.List;
import java.util.Map;

public abstract class Command {

  private String commandReturn;
  private Map<String, String> map;
  protected int numberOfArgs;
  protected Boolean store = false;
  protected double repeat = Double.MIN_VALUE ;
  private double repeatNumber=0;

  public Command(){
  }

  public Command(List<String> varargs, Control control) {}
  public String commandValueReturn(){
    return commandReturn;
  }
  public Map<String, String> getVariablesCreated(){return map;}
  public int getNumberOfArgs(){return numberOfArgs;}
  public double repeatCom(){return repeatNumber;}
  public double runnable(){return repeat;}
  public boolean storeCommands(){ return store; }

}
