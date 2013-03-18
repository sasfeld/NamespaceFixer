/**
 @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class FixTab extends GeneralTab {
  public class ExecuteFixingBtnListener implements ActionListener {
    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      if (currentSelection.selectionPerformed()) {
        setChanged();
        notifyObservers(currentSelection); // notify controller
        System.out.println("idk observers are notified...");
      } else {
        FixerGui.getInstance().showErrorMessage(new Exception("Please choose an input file/folder and an output folder"));
      }
    }

  }

  public static final String TAB_TITLE = "Start fixing";
  public static final Icon TAB_ICON = null;
  public static final String TAB_TOOLTIPS = "Execute fixing here";
  private static final String FIXING_BUTTON_LABEL = "Execute fixing";
  private JTextArea logField;

  public FixTab() {
    super(TAB_TITLE, TAB_ICON, null, TAB_TOOLTIPS);
    addSpecificComponents();
  }

  private void addSpecificComponents() {
    final JButton executeFixingBtn = new JButton(FIXING_BUTTON_LABEL);
    executeFixingBtn.addActionListener(new ExecuteFixingBtnListener());
    super.components.add(executeFixingBtn);

    logField = new JTextArea();
    logField.setEditable(false);
    super.components.add(logField);
  }

  /**
   * Fetch the text of the logField.
   * 
   * @return a String or null (if the textfield wasn't initialized yet)
   */
  public String getLogText() {
    if (logField != null) {
      return logField.getText();
    }
    return null;
  }

  /**
   * Set the text of the logField.
   * 
   * @param text
   *          the String
   * @param append
   *          true if you want to append the string. A newline will be added.
   */
  public void setLogText(final String text, final boolean append) {
    String preString = "";
    if (append) {
      preString = getLogText() + "\n";
    }

    if (logField != null && preString != null) {
      logField.setText(preString + text);
    }
  }
}
