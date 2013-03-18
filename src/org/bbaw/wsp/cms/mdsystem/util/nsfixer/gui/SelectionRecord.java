/**
 * 
 */
package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.io.File;
import java.io.Serializable;

/**
 * Instances of this class hold the user's selections so that those can easily be handled by an observer or easily can be saved.
 * 
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class SelectionRecord implements Serializable {
  private static final long serialVersionUID = 6227801959711608765L;
  private File inputFile;
  private File outputFile;

  /**
   * @return the inputFile
   */
  public final File getInputFile() {
    return inputFile;
  }

  /**
   * @param inputFile
   *          the inputFile to set
   */
  public final void setInputFile(final File inputFile) {
    this.inputFile = inputFile;
  }

  /**
   * @return the outputFile
   */
  public final File getOutputFile() {
    return outputFile;
  }

  /**
   * @param outputFile
   *          the outputFile to set
   */
  public final void setOutputFile(final File outputFile) {
    this.outputFile = outputFile;
  }

  /**
   * Check if the user has selected anything.
   * 
   * @return true if the selection was performed by the user.
   */
  public boolean selectionPerformed() {
    return (inputFile != null && outputFile != null);
  }

}
