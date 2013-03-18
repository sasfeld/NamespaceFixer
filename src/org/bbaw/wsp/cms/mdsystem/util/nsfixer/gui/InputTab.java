/**
 @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class InputTab extends GeneralTab {

  public class InputFileActionListener implements ActionListener {
    @Override
    public void actionPerformed(final ActionEvent event) {
      final File inputFile = GuiUtils.showOpenFileChooser(null, new RdfAndFolderFileFilter(), true);
      if (inputFile != null) {
        aktSelectionLabel.setText("Currently selected: " + inputFile.getAbsolutePath());
        currentSelection.setInputFile(inputFile);
      }
    }
  }

  public static final String TAB_TITLE = "Input";
  public static final Icon TAB_ICON = null;
  public static final String TAB_TOOLTIPS = "Specify fields to be fixed here.";
  private static final String DEFAULT_LABEL = "You haven't selected any input file or folder yet.";
  private static final String FILE_BUTTON_LABEL = "Select file";
  private JLabel aktSelectionLabel;
  private JButton specifyFileBtn;

  public InputTab() {
    super(TAB_TITLE, TAB_ICON, null, TAB_TOOLTIPS);
    addSpecificComponents();
  }

  private void addSpecificComponents() {
    String initializeLabel = DEFAULT_LABEL;
    if (currentSelection.selectionPerformed()) {
      initializeLabel = "Currently selected: " + currentSelection.getInputFile();
    }
    aktSelectionLabel = new JLabel(initializeLabel);
    super.components.add(aktSelectionLabel);

    specifyFileBtn = new JButton(FILE_BUTTON_LABEL);
    specifyFileBtn.addActionListener(new InputFileActionListener());
    super.components.add(specifyFileBtn);
  }
}
