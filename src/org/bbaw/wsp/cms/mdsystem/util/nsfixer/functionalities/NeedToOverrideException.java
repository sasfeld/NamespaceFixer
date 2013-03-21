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
  private final boolean isDir;

  /**
   * This exception should be thrown if a file already exists and the user shall confirm in overriding it.
   * 
   * @param msg
   *          String the message tho show
   * @param title
   *          String the title for the confirm dialog
   * @param outputFile
   *          {@link File} the selected output file (which already exists)
   * @param inputFile
   *          the current input file
   * @param isDir
   *          boolean true if the general surrounding input file was a directory
   */
  public NeedToOverrideException(final String msg, final String title, final File outputFile, final File inputFile, final boolean isDir) {
    super(msg);
    this.title = title;
    this.outputFile = outputFile;
    this.inputFile = inputFile;
    this.isDir = isDir;
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

  /**
   * @return the isDir
   */
  public final boolean isDir() {
    return isDir;
  }

}
