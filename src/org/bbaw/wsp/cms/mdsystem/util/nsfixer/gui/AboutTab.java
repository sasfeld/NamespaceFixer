/**
 @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import javax.swing.Icon;
import javax.swing.JTextArea;

public class AboutTab extends GeneralTab {
  public static final String TAB_TITLE = "About";
  public static final Icon TAB_ICON = null;
  public static final String TAB_TOOLTIPS = "General information";

  public AboutTab() {
    super(TAB_TITLE, TAB_ICON, null, TAB_TOOLTIPS);
    addSpecificComponents();
  }

  private void addSpecificComponents() {
    final JTextArea infoBox = new JTextArea();
    infoBox.setText("NamespaceFixerTool \n\nThis tool is only designed for usage within the Wissensspeicher.\n\nCopyright (c) 2013 Wissensspeicher, Berlin Brandenburgische Akademie der Wissenschaften. \n\nSupport: Contact Sascha Feldmann - wsp-shk1@bbaw.de");
    infoBox.setEditable(false);
    infoBox.setSize(FixerGui.GUI_WIDTH, 300);
    super.components.add(infoBox);
  }
}
