/**
 * 
 */
package org.bbaw.wsp.cms.mdsystem.util.nsfixer.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * A file filter which accepts directores
 * 
 * @author <a href="mailto:wsp-shk1@bbaw.de">Sascha Feldmann</a>
 * @since 18.03.2013
 * 
 */
public class FolderFileFilter extends FileFilter {
  private static final String FILTER_DESCRIPTION = "Folders only";

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
   */
  @Override
  public boolean accept(final File f) {
    return f.isDirectory();
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.swing.filechooser.FileFilter#getDescription()
   */
  @Override
  public String getDescription() {
    return FILTER_DESCRIPTION;
  }

}
