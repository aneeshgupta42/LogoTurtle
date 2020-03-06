package controller;

public class ListObjects {

  private String myList;
  private Boolean canBeRun;

  public ListObjects(String list){
    myList = list;
    canBeRun = true;
  }

  public String getMyList(){
    return myList;
  }

  public void cannotBeRun(Boolean canIt){
    canBeRun = !canIt;
  }

  public boolean canBeRun(){
      return  canBeRun;
  }

}
