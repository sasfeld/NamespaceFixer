/**
 @author: <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 @since: 18.03.2013
 */

package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * General tab for an unique look & feel.
 * 
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class GeneralTab extends Observable {

  private final String tabTitle;
  private final Icon tabIcon;
  protected final List<JComponent> components;
  private final String tabTooltip;
  private final JPanel mainPanel;

  /*
   * --- current selection ---
   */
  protected final SelectionRecord currentSelection;

  /**
   * Create a new tab.
   * 
   * @param tabTitle
   * @param tabIcon
   * @param components
   * @param tabTooltip
   */
  public GeneralTab(final String tabTitle, final Icon tabIcon, final List<JComponent> components, final String tabTooltip) {
    this.tabTitle = tabTitle;
    this.tabIcon = tabIcon;
    if (components == null) {
      this.components = new ArrayList<JComponent>();
    } else {
      this.components = components;
    }
    this.tabTooltip = tabTooltip;

    currentSelection = FixerGui.getCurrentSelection();
    mainPanel = new JPanel();
    setMainLayout();
  }

  private void setMainLayout() {
    final BorderLayout borderLayout = new BorderLayout();
    mainPanel.setLayout(borderLayout);
    borderLayout.setVgap(15); // vertical distance between components
    borderLayout.setHgap(15); // horicontal distance between components
  }

  /**
   * @return the tabTitle
   */
  public final String getTabTitle() {
    return tabTitle;
  }

  /**
   * @return the tabIcon
   */
  public final Icon getTabIcon() {
    return tabIcon;
  }

  /**
   * @return the components
   */
  public final JPanel getPanel() {
    if (mainPanel.getComponentCount() == 0) {
      for (final JComponent component : components) {
        if (component instanceof JButton) { // specific wrapping for buttons
          final JPanel wrapperPanel = new JPanel();
          wrapperPanel.add(component);
          wrapperPanel.setSize(FixerGui.GUI_WIDTH, component.getSize().height + 10);
          mainPanel.add(BorderLayout.EAST, wrapperPanel);
        } else if (component instanceof JTextArea) {
          component.setSize(FixerGui.GUI_WIDTH, component.getSize().height + 100);
          final JScrollPane scroller = new JScrollPane(component);
          scroller.setSize(FixerGui.GUI_WIDTH, component.getSize().height + 100);
          scroller.setMaximumSize(new Dimension(FixerGui.GUI_WIDTH, component.getSize().height + 100));
          mainPanel.add(BorderLayout.CENTER, scroller);
        } else {
          mainPanel.add(BorderLayout.NORTH, component);
        }
      }
    }
    return mainPanel;
  }

  /**
   * @return the tabTooltip
   */
  public final String getTabTooltip() {
    return tabTooltip;
  }
}
