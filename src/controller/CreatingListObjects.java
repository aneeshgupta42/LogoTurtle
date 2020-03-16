package controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
@author Libba Lawrence

Creates list objects for each commandInput
 */
public class CreatingListObjects {

  private static final String LIST_START = "[";
  private static final String LIST_END = "]";
  private List<ListObjects> groupList = new ArrayList<>();;
  private int currentRepeatNumber;
  private String commandInput;


  public CreatingListObjects(){ }

  /*
  Returns the list of the list objects
  @return groupList
   */
  public List<ListObjects> getLists(){return groupList;}

  /*
  Organizes all the lists in the commandInput
  @param commandInput, currentRepeatNumber
   */
  public void findLists(String commandInput,int currentRepeatNumber) {
    this.currentRepeatNumber = currentRepeatNumber;
    this.commandInput = commandInput;
    LinkedList<Integer> starts = new LinkedList<>();
    LinkedList<Integer> ends = new LinkedList<>();
    int startIndex = commandInput.indexOf(LIST_START);
    int numStarts = 0;
    while (startIndex >= 0) {
      starts.push(startIndex);
      numStarts++;
      startIndex = commandInput.indexOf(LIST_START, startIndex + 1);
    }
    int endIndex = commandInput.indexOf(LIST_END);
    while (endIndex >= 0) {
      ends.push(endIndex);
      endIndex = commandInput.indexOf(LIST_END, endIndex + 1);
    }
    LinkedList<ArrayList<Integer>> sets = matchingLists(starts, ends, numStarts);
    createListObjects(sets);
  }

  private LinkedList matchingLists(LinkedList<Integer> starts, LinkedList<Integer> ends, int numStarts) {
    LinkedList<ArrayList<Integer>> sets = new LinkedList<>();
    ArrayList<Integer> two = new ArrayList<>();
    two = linkListPairs(starts, ends, numStarts, sets, two);
    resetInput(sets, two);
    return sets;
  }

  private ArrayList<Integer> linkListPairs(LinkedList<Integer> starts, LinkedList<Integer> ends,
      int numStarts, LinkedList<ArrayList<Integer>> sets, ArrayList<Integer> two) {
    while (numStarts > 0 && starts.size()==ends.size()) {
      int first = starts.pollLast();
      int last = ends.pollLast();
      for (int whichEnd : starts) {
        if (whichEnd < last) {
          int store = last;
          last = ends.pollLast();
          ends.push(store);
        }
      }
      numStarts--;
      two.add(first + 1);
      two.add(last -1);
      sets.add(two);
      two = new ArrayList<>();
    }
    return two;
  }

  private void resetInput(LinkedList<ArrayList<Integer>> sets, ArrayList<Integer> two) {
    if (currentRepeatNumber == 1) {
      if (sets.size() != 1) {
        two.add(0);
        two.add(commandInput.length());
        sets.add(two);
      }
    }
  }

  private void createListObjects(LinkedList<ArrayList<Integer>> sets) {
    ArrayList<Integer> set;
    while (sets.size() != 0) {
      set = sets.pop();
      int first = set.get(0);
      int end = set.get(1);
      groupList.add(new ListObjects(commandInput.substring(first, end)));
    }
  }


}
