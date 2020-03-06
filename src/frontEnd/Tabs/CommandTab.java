package frontEnd.Tabs;

import frontEnd.Windows.CommandWindow;

public class CommandTab extends StoredElementsTab {
  private static final String COMMAND_TAB_TITLE = "User Commands";

  public CommandTab(CommandWindow commandWindow) {
    super(commandWindow);
    setText(COMMAND_TAB_TITLE);
  }
}
