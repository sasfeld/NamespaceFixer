/**
 @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class OutputTab extends GeneralTab {
  public class OutputFileActionListener implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent event) {
      final File dir = FixerGui.getCurrentSelection().getInputFile();

      String path = null;
      if (dir != null) {
        path = dir.getAbsolutePath();
      }
      final File outputFile = GuiUtils.showOpenFileChooser(path, new FolderFileFilter(), true);
      if (outputFile != null) {
        aktSelectionLabel.setText("Currently selected: " + outputFile.getAbsolutePath());
        currentSelection.setOutputFile(outputFile);
      }
    }
  }

  public static final String TAB_TITLE = "Output";
  public static final Icon TAB_ICON = null;
  public static final String TAB_TOOLTIPS = "Specify output file/folder here.";
  private static final String DEFAULT_LABEL = "You haven't selected any output folder yet.";
  private static final String FILE_BUTTON_LABEL = "Select folder";
  private JLabel aktSelectionLabel;
  private AbstractButton specifyFileBtn;

  public OutputTab() {
    super(TAB_TITLE, TAB_ICON, null, TAB_TOOLTIPS);
    addSpecificComponents();
  }

  private void addSpecificComponents() {
    String initializeLabel = DEFAULT_LABEL;
    if (currentSelection.selectionPerformed()) {
      initializeLabel = "Currently selected: " + currentSelection.getOutputFile();
    }
    aktSelectionLabel = new JLabel(initializeLabel);
    super.components.add(aktSelectionLabel);

    specifyFileBtn = new JButton(FILE_BUTTON_LABEL);
    specifyFileBtn.addActionListener(new OutputFileActionListener());
    super.components.add(specifyFileBtn);
  }
}
