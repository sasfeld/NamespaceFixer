/**
 * 
 */
package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.io.File;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.bbaw.wsp.cms.mdsystem.util.nsfixer.NamespaceFixerController;
import org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities.NeedToOverrideException;

/**
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class FixerGui extends JFrame {
  private static final long serialVersionUID = 1L;
  public static final int GUI_HEIGHT = 600;
  public static final int GUI_WIDTH = 800;
  /**
   * Title of all error messages.
   */
  private static final String ERROR_TITLE = "Error";
  private static final String GUI_TITLE = "NamespaceFixer Tool V21_03_13";
  private static FixerGui instance;
  private FixTab fixerTab;
  private static SelectionRecord currentSelection;

  private FixerGui() {
    buildGui();
  }

  /**
   * Grab the only existing FixerGui.
   * 
   * @return
   */
  public static FixerGui getInstance() {
    if (instance == null) {
      currentSelection = new SelectionRecord();
      loadSelection();
      instance = new FixerGui();
    }
    return instance;
  }

  private static File getTargetDir() {
    final File f = new File(System.getProperty("user.home"), NamespaceFixerController.SAVE_DIR_NAME);
    if (!f.exists()) {
      f.mkdir();
    }
    return f;
  }

  private static void loadSelection() {
    final File targetDir = getTargetDir();
    final SelectionRecord loadedSelection = GuiUtils.loadSettings(targetDir);
    if (loadedSelection != null) {
      FixerGui.setCurrentSelection(loadedSelection);
    }
  }

  /**
   * Add all the components to the GUI here.
   */
  private void buildGui() {
    setTitle(GUI_TITLE);
    final JTabbedPane tabbedPane = new JTabbedPane();
    final GeneralTab inputTab = new InputTab();
    final GeneralTab outputTab = new OutputTab();
    fixerTab = new FixTab();
    final GeneralTab aboutTab = new AboutTab();

    tabbedPane.addTab(inputTab.getTabTitle(), inputTab.getTabIcon(), inputTab.getPanel(), inputTab.getTabTooltip());
    tabbedPane.addTab(outputTab.getTabTitle(), outputTab.getTabIcon(), outputTab.getPanel(), outputTab.getTabTooltip());
    tabbedPane.addTab(fixerTab.getTabTitle(), fixerTab.getTabIcon(), fixerTab.getPanel(), fixerTab.getTabTooltip());
    tabbedPane.addTab(aboutTab.getTabTitle(), aboutTab.getTabIcon(), aboutTab.getPanel(), aboutTab.getTabTooltip());

    this.add(tabbedPane);
    this.setSize(GUI_WIDTH, GUI_HEIGHT);
  }

  /**
   * Add a general observer who gets informed if the the fixing job is started.
   * 
   * @param o
   *          the observer object
   */
  public void addObserver(final Object o) {
    if (o instanceof Observer) {
      fixerTab.addObserver((Observer) o);
    }
  }

  /**
   * Show a general error message.
   * 
   * @param e
   *          an {@link Exception}
   */
  public void showErrorMessage(final Exception e) {
    JOptionPane.showMessageDialog(this, e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Show a general confirm dialog.
   * 
   * @param e
   *          the {@link NeedToOverrideException}
   * @return true if the user confirmed (YES)
   */
  public boolean showConfirmDialog(final NeedToOverrideException e) {
    final int userRet = JOptionPane.showConfirmDialog(this, e.getMessage(), e.getTitle(), JOptionPane.YES_NO_OPTION);
    if (userRet == JOptionPane.YES_OPTION) {
      return true;
    }
    return false;
  }

  /**
   * Add a message to the UI log.
   * 
   * @param message
   *          a String
   */
  public void addToLog(final String message) {
    fixerTab.setLogText(message, true);
  }

  /**
   * 
   * @return
   */
  public static SelectionRecord getCurrentSelection() {
    return currentSelection;
  }

  /**
   * set current selection
   * 
   * @param loadedSelection
   */
  public static void setCurrentSelection(final SelectionRecord loadedSelection) {
    if (loadedSelection != null) {
      currentSelection = loadedSelection;
    }

  }
}
