package frontEnd.Tabs;

import frontEnd.Windows.CommandWindow;

public class VariableTab extends StoredElementsTab {
  private static final String VARIABLE_TAB_TITLE = "Variables";

  public VariableTab(CommandWindow commandWindow) {
    super(commandWindow);
    setText(VARIABLE_TAB_TITLE);
  }
}
