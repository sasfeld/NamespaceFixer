/**
 @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

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
  // @formatter:off
  private static final String INFO_TEXT = "What is this tool designed for?\n\n"
            + "- It removes irregular namespaces produced by TopBraid, like j.0 and so on.\n"
            + "- You can specifiy a single .rdf file or a whole folder as input and an output folder. \n"
            + "Files will be added to the output folder and be specified by their input name.\n\n"
            + "Each generated file will get checked for its XML validity.\n"
            + "If one of the generated file isn't xml valid you have to check it yourself.";
  //@formatter:on
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
    final Color textFieldColor = Color.BLUE;
    aktSelectionLabel.setForeground(textFieldColor);
    super.components.add(aktSelectionLabel);

    specifyFileBtn = new JButton(FILE_BUTTON_LABEL);
    specifyFileBtn.addActionListener(new InputFileActionListener());
    super.components.add(specifyFileBtn);

    /*
     * information text area
     */
    final JTextArea infoArea = new JTextArea();
    infoArea.setText(INFO_TEXT);
    infoArea.setEditable(false);

    super.components.add(infoArea);
  }
}
