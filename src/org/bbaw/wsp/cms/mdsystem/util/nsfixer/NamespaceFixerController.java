/**
 @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities.InvalidSelectionException;
import org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities.NamespaceFixer;
import org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities.NeedToOverrideException;
import org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui.FixerGui;
import org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui.GuiUtils;
import org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui.SelectionRecord;

/**
 * This controller calls the GUI and reacts on user input.
 * 
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class NamespaceFixerController implements Observer {
  public static final String SAVE_DIR_NAME = "wsp_namespace_fixer";

  public class GuiCloseListener extends WindowAdapter {

    @Override
    public void windowOpened(final WindowEvent event) {
      final File targetDir = getTargetDir();
      final SelectionRecord loadedSelection = GuiUtils.loadSettings(targetDir);
      if (loadedSelection != null) {
        FixerGui.setCurrentSelection(loadedSelection);
      }
    }

    @Override
    public void windowClosing(final WindowEvent event) {
      if (FixerGui.getCurrentSelection().selectionPerformed()) {
        final File targetDir = getTargetDir();
        GuiUtils.saveSettings(targetDir, FixerGui.getCurrentSelection());
      }
    }

    private File getTargetDir() {
      final File f = new File(System.getProperty("user.home"), SAVE_DIR_NAME);
      if (!f.exists()) {
        f.mkdir();
      }
      return f;
    }

  }

  private final NamespaceFixer namespaceFixer;
  private final FixerGui gui;

  /**
   * Create a new controller. It will call and show the GUI.
   */
  public NamespaceFixerController() {
    namespaceFixer = new NamespaceFixer();
    namespaceFixer.addObserver(this);
    gui = FixerGui.getInstance();
    gui.addWindowListener(new GuiCloseListener());
    gui.setVisible(true);
    gui.addObserver(this);
  }

  /**
   * Start a fixing job.
   * 
   * @param selection
   *          the {@link SelectionRecord}.
   * @param forceOverride
   *          true if you want to force overriding files.
   */
  public void startFixing(final SelectionRecord selection, final boolean forceOverride) {
    try {
      if (selection.selectionPerformed()) {
        if (selection.getInputFile().isDirectory()) {
          namespaceFixer.fixDir(selection.getInputFile(), selection.getOutputFile(), forceOverride);
        } else {
          namespaceFixer.fixFile(selection.getInputFile(), selection.getOutputFile());
        }
      }
    } catch (final InvalidSelectionException e) {
      gui.showErrorMessage(e);
    } catch (final NeedToOverrideException e) {
      final boolean force = gui.showConfirmDialog(e);
      if (force) {
        namespaceFixer.skipTo(e.getInputFile());
        try {
          namespaceFixer.fixFile(e.getInputFile(), e.getOutputFile());
          startFixing(selection, false);
        } catch (final InvalidSelectionException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    }
  }

  @Override
  /*
   * (non-Javadoc)
   * 
   * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
   */
  public void update(final Observable o, final Object transmittedObject) {
    if (transmittedObject instanceof SelectionRecord) { // information by the FixTab
      final SelectionRecord selection = (SelectionRecord) transmittedObject;
      System.out.println("starting fixing");
      startFixing(selection, false);
    } else if (transmittedObject instanceof String) { // information by the NamespaceFixer
      gui.addToLog((String) transmittedObject);
    }
  }
}
