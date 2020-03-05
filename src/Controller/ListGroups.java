package Controller;

import java.util.List;

public class ListGroups {

  private String myList;
  private Boolean canBeRun;

  public ListGroups(String list){
    myList = list;
    canBeRun = true;
  }

  public String getMyList(){
    return myList;
  }

  public void cannotBeRun(){
    canBeRun = false;
  }

  public boolean canBeRun(){
      return  canBeRun;
  }

}
