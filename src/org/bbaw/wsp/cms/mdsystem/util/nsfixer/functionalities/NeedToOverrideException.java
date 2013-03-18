/**
 * 
 */
package org.bbaw.wsp.cms.mdsystem.util.nsfixer.functionalities;

import java.io.File;

/**
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class NeedToOverrideException extends Exception {
  static final long serialVersionUID = -6296936363450381931L;
  private final String title;
  private final File outputFile;
  private final File inputFile;

  public NeedToOverrideException(final String msg, final String title, final File outputFile, final File inputFile) {
    super(msg);
    this.title = title;
    this.outputFile = outputFile;
    this.inputFile = inputFile;
  }

  /**
   * 
   * @return this exception's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the outputDir
   */
  public final File getInputFile() {
    return inputFile;
  }

  /**
   * @return the childToOverwrite
   */
  public final File getOutputFile() {
    return outputFile;
  }

}
