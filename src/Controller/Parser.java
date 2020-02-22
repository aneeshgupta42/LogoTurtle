package Controller;

import backEnd.ErrorHandler;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Parser {

  private static final String RESOURCES_PACKAGE = "resources.languages.";
  // "types" and the regular expression patterns that recognize those types
  // note, it is a list because order matters (some patterns may be more generic)
  private List<Entry<String, Pattern>> mySymbols;


  /**
   * Create an empty parser
   */
  public Parser() {
    mySymbols = new ArrayList<>();
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addPatterns(String syntax) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + syntax);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      mySymbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  public String getSymbol(String text) {
    for (Entry<String, Pattern> e : mySymbols) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    ErrorHandler.handle();
    return null;
  }


  // Returns true if the given text matches the given regular expression pattern
  private boolean match(String text, Pattern regex) {
    return regex.matcher(text).matches();
  }
}
