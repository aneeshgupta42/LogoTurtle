package controller;

/*
@author Libba Lawrence

Creates a listObject
 */
public class ListObjects {

  private String myList;
  private Boolean canBeRun;

  /*
  Initializes variables
  @param list
   */
  public ListObjects(String list){
    myList = list;
    canBeRun = true;
  }

  /*
  Returns the string associated with the brackets
  @return myList
   */
  public String getMyList(){
    return myList;
  }

  /*
  Sets whether the list can be run
 @param canIt
   */
  public void cannotBeRun(Boolean canIt){
    canBeRun = !canIt;
  }

  /*
  Returns the boolean on if it can be run
  @return canBeRun
   */
  public boolean canBeRun(){
      return  canBeRun;
  }

}
